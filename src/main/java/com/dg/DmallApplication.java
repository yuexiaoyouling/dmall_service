package com.dg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.dg.business.dao.**","com.dg.business.dao.provider.**"})
public class DmallApplication {
    public static void main(String[] args) {
        SpringApplication.run(DmallApplication.class);
    }
}
