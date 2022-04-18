package com.kifiyapro.bunchi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootTest
//@EnableConfigurationProperties({FileStorageProperties.class})
@EnableSwagger2
@EnableScheduling
class BunchiApplicationTests {

    @Test
    void contextLoads() {
    }

}
