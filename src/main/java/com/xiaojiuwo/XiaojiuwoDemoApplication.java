package com.xiaojiuwo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.xiaojiuwo.mapper")
public class XiaojiuwoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaojiuwoDemoApplication.class, args);
    }

}
