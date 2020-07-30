package com.stone.demo.sign;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignApplication.class,args);
    }
}
