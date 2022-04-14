package com.kifiyapro.bunchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BunchiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BunchiApplication.class, args);
    }

}
