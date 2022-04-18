package com.kifiyapro.bunchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
//@EnableConfigurationProperties({FileStorageProperties.class})
@EnableSwagger2
@EnableScheduling
public class BunchiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BunchiApplication.class, args);
    }



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(BunchiApplication.class);
    }
}
