package com.bj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.bj.dao")
public class ItemServiceSpringApplication {

    public static void main(String[] args){
        SpringApplication.run(ItemServiceSpringApplication.class,args);
    }
}
