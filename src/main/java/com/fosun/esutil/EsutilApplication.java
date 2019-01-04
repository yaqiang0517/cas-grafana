package com.fosun.esutil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//防止报错
@EnableScheduling//使用@Scheudle开启定时器
public class EsutilApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsutilApplication.class, args);
    }

}

