package com.shopease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ShopEaseApplication - Entry point for the ShopEase E-Commerce REST API.
 * 
 * Mentoring Note for Students:
 * @SpringBootApplication encapsulates @Configuration, @EnableAutoConfiguration,
 * and @ComponentScan, which scans all components under the com.shopease package.
 */
@SpringBootApplication
public class ShopEaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopEaseApplication.class, args);
    }
}
