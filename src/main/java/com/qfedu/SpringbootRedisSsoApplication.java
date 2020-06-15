package com.qfedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qfedu.mapper")
public class SpringbootRedisSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisSsoApplication.class, args);
    }

}
