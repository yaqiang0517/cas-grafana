package com.fosun.esutil.task;

import com.alibaba.fastjson.JSON;
import com.fosun.esutil.core.ESClient;
import com.fosun.esutil.core.Product;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName Schedule
 * @Description TODO
 * @Author zhangyq
 * @Date 2019/1/4 10:06
 **/
@Component
public class Schedule {

    public static int lenovo_stock = 10000;
    public static int hp_stock = 10000;
    public static long lenovo_productId = 1001L;
    public static long hp_productId = 1002L;
    public static String lenovo_productName = "联想笔记本";
    public static String hp_productName = "惠普笔记本";

//    @Scheduled(cron = "0/2 * * * * ?")
    public void index(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("*************索引数据到ES " + sdf.format(new Date()));
        Product product;
        int sales = new Random().nextInt(10);
        if(sales%2==1){
            lenovo_stock = lenovo_stock - new Random().nextInt(10);
            product = new Product(lenovo_productId,lenovo_productName,lenovo_stock,System.currentTimeMillis()+"");
        }else{
            hp_stock = hp_stock - new Random().nextInt(10);
            product = new Product(hp_productId,hp_productName,hp_stock,System.currentTimeMillis()+"");
        }

        System.out.println("*************product " + JSON.toJSONString(product));
        ESClient.getClient().prepareIndex("monitor_index", "monitor_type").setSource(JSON.toJSONString(product)).get();
        System.out.println("*************索引结束");
    }
}