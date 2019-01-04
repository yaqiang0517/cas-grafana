package com.fosun.esutil.core;


import com.fosun.esutil.bean.CommResult;
import com.fosun.esutil.bean.CreateIndexRequest;
import com.fosun.esutil.bean.FieldProperty;
import com.fosun.esutil.exception.BizException;
import net.sf.json.JSONObject;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import org.springframework.util.StringUtils;


import java.util.*;

/**
 * es部分操作
 */
public class ESOperation {


    /**
     * 索引的mapping
     * <p>
     * 预定义一个索引的mapping,使用mapping的好处是可以个性的设置某个字段等的属性
     * mapping 类似于预设某个表的字段类型
     * <p>
     * Mapping,就是对索引库中索引的字段名及其数据类型进行定义，类似于关系数据库中表建立时要定义字段名及其数据类型那样，
     * 不过es的 mapping比数据库灵活很多，它可以动态添加字段。
     * 一般不需要要指定mapping都可以，因为es会自动根据数据格式定义它的类型，
     * 如果你需要对某 些字段添加特殊属性（如：定义使用其它分词器、是否分词、是否存储等），就必须手动添加mapping。
     * 有两种添加mapping的方法，一种是定义在配 置文件中，一种是运行时手动提交mapping，两种选一种就行了。
     *
     */
    public static CommResult buildIndexMapping(CreateIndexRequest request) {
        Map<String, Object> settings = new HashMap<>();
        settings.put("number_of_shards", request.getShards());//分片数量
        settings.put("number_of_replicas", request.getReplicas());//复制数量

        String indexName = request.getIndexName();
        String typeName = request.getTypeName();
        List<FieldProperty> fieldInfo = request.getFieldInfo();
        XContentBuilder mapping;
        Client client = ESClient.getClient();
        try {  
            CreateIndexRequestBuilder cib= client.admin().indices().prepareCreate(indexName).setSettings(settings);
            mapping = XContentFactory.jsonBuilder().startObject().startObject("properties"); //设置之定义字段
            for(FieldProperty info : fieldInfo){  
                String field = info.getField(); 
                String type = info.getType();  
                if(type == null || "".equals(type.trim())){  
                	type = "keyword";//默认是keyword类型  
                } 
                mapping.startObject(field)  
                .field("type",type);
                if(!StringUtils.isEmpty(info.getAnalyer())){
                	mapping.field("analyzer",info.getAnalyer());
                }
                if(!StringUtils.isEmpty(info.getIndex())){
                	mapping.field("index",info.getIndex());
                }
                if(!StringUtils.isEmpty(info.getStore())){
                	mapping.field("store",info.getStore());
                }
                if(!StringUtils.isEmpty(info.getFormat())){
                	mapping.field("format",info.getFormat());
                }
                mapping.endObject(); 
            }  
            mapping.endObject().endObject();  
            cib.addMapping(typeName, mapping);  
            cib.execute().actionGet();  
        }catch (ResourceAlreadyExistsException e){
            throw new BizException(-1, "已经有同名的索引或者类型");
        }catch (Exception e) {
            e.printStackTrace();
            throw new BizException(-1, "创建索引发生异常");
        }
        return new CommResult(0, "创建映射成功");
        
    } 



    
    /**
     * 添加/编辑es中某条数据
     * @param indexName
     * @param typeName
     * @param data
     */
    public static void index(String indexName, String typeName,String docId,String data) {
    	ESClient.getClient().prepareIndex(indexName, typeName).setId(docId).setSource(data).get();
    }

    /**
     * 批量添加记录到索引
     * @param indexName
     * @param typeName
     * @return
     */
    public static boolean indexBathImport(String indexName, String typeName,Map<String,String> docs) {
    	Client client = ESClient.getClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		docs.forEach((k,v)->{
			String docId = k;
			JSONObject doc = JSONObject.fromObject(v);
			IndexRequestBuilder lrb = client.prepareIndex(indexName, typeName).setId(docId).setSource(doc);
			bulkRequest.add(lrb);
		});
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println(bulkResponse.getItems().toString());
			return false;
		}
		return true;
	}
    
}
