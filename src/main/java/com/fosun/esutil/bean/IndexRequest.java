package com.fosun.esutil.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TestIndexRequest
 * @Description TODO
 * @Author zhangyq
 * @Date 2018/12/29 10:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexRequest {
    private String indexName;
    private String typeName;
    private Object data;
}
