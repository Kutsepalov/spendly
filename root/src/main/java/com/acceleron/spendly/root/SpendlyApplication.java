package com.acceleron.spendly.root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.acceleron.spendly")
@SpringBootApplication
public class SpendlyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpendlyApplication.class, args);
    }
}
