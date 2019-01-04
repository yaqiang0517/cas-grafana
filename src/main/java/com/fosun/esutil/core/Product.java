package com.fosun.esutil.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Product
 * @Description TODO
 * @Author zhangyq
 * @Date 2018/12/29 15:12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productId;
    private String productName;
    private Integer stock;
    private String updateTime;
}
