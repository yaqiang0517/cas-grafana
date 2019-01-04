package com.fosun.esutil.util;

import com.alibaba.fastjson.JSON;
import com.fosun.esutil.bean.*;
import com.fosun.esutil.config.PropertiesUtil;
import com.fosun.esutil.core.ESClient;
import com.fosun.esutil.core.ESOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @ClassName ES操作工具类
 * @Description TODO
 * @Author zhangyq
 * @Date 2019/1/2 16:27
 **/
@Slf4j
public class ESUtil {

    /**
     * 创建索引
     * @param request
     * @return
     */
    public static CommResult createIndex(CreateIndexRequest request) {
        log.info("createIndex入参：" + JSON.toJSONString(request));
        if(null == request.getShards() || 0 == request.getShards()){
            request.setShards(Integer.parseInt(PropertiesUtil.getProperties().get("es.number_of_shards")));
        }
        if(null == request.getReplicas() || 0 == request.getReplicas()){
            request.setReplicas(Integer.parseInt(PropertiesUtil.getProperties().get("es.number_of_replicas")));
        }
        return ESOperation.buildIndexMapping(request);
    }

    /**
     * 索引数据
     * @param request
     * @return
     */
    public static CommResult index(IndexRequest request) {
        log.info("index入参：" + JSON.toJSONString(request));
        String data = JSON.toJSONString(request.getData());
        ESClient.getClient().prepareIndex(request.getIndexName(), request.getTypeName()).setSource(data).get();
        return new CommResult(0, "请求成功");
    }



    public static void main(String[] args) {
        CreateIndexRequest request = new CreateIndexRequest();
        request.setReplicas(3);
        request.setIndexName("monitor_index2");
        request.setTypeName("monitor_type2");

        FieldProperty property1 = new FieldProperty();
        property1.setAnalyer("ik_max_word");
        property1.setIndex("true");
        property1.setType("text");
        property1.setField("test");

        FieldProperty property2 = new FieldProperty();
        property2.setIndex("true");
        property2.setType("text");
        property2.setField("test2");

        request.setFieldInfo(Arrays.asList(property1,property2));

        createIndex(request);

        Test test = new Test("test1","test2");

        IndexRequest request1 = new IndexRequest();
        request1.setIndexName("monitor_index2");
        request1.setTypeName("monitor_type2");
        request1.setData(test);
        System.out.println(index(request1));
    }
}
