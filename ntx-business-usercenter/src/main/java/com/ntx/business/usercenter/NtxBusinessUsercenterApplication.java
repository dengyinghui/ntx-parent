package com.ntx.business.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ntx.business.usercenter.mapper")
@ComponentScan(value = "com.ntx")
public class NtxBusinessUsercenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(NtxBusinessUsercenterApplication.class, args);
    }

}
