package com.fosun.esutil.controller;

import com.alibaba.fastjson.JSON;

import com.fosun.esutil.bean.CommResult;
import com.fosun.esutil.bean.CreateIndexRequest;
import com.fosun.esutil.bean.IndexRequest;
import com.fosun.esutil.core.ESClient;
import com.fosun.esutil.core.ESOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName Monitor
 * @Description TODO
 * @Author zhangyq
 * @Date 2018/12/28 18:46
 **/

@RestController
@Slf4j
@PropertySource("classpath:es.properties")
public class ESController {

    @Value("${es.number_of_shards}")
    private Integer shards;

    @Value("${es.number_of_replicas}")
    private Integer replicas;


    @PostMapping("/createIndex")
    public CommResult createIndex(@RequestBody CreateIndexRequest request) throws Exception {
        log.info("createIndex入参：" + JSON.toJSONString(request));
        if(null == request.getShards() || 0 == request.getShards()){
            request.setShards(shards);
        }
        if(null == request.getReplicas() || 0 == request.getReplicas()){
            request.setReplicas(replicas);
        }
        return ESOperation.buildIndexMapping(request);
    }

    @PostMapping("/index")
    public CommResult index(@RequestBody IndexRequest request) {
        log.info("index入参：" + JSON.toJSONString(request));
        String data = JSON.toJSONString(request.getData());
        ESClient.getClient().prepareIndex(request.getIndexName(), request.getTypeName()).setSource(data).get();
        return new CommResult(0, "请求成功");
    }

}
