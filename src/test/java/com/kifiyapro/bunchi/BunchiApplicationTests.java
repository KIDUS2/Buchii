package com.kifiyapro.bunchi;

import com.kifiyapro.bunchi.Service.AdoptService;
import com.kifiyapro.bunchi.Service.CustomerService;
import com.kifiyapro.bunchi.repository.AdoptRepository;
import com.kifiyapro.bunchi.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootTest
//@EnableConfigurationProperties({FileStorageProperties.class})
@EnableSwagger2
@EnableScheduling
class BunchiApplicationTests {
    @Autowired
    private CustomerService customerService;
    private AdoptService adoptService;

    @MockBean
    private CustomerRepository customerRepository;
    private AdoptRepository adoptRepository;

    @Test
    void contextLoads() {
    }

//public void add_customer(){
//        when(customerRepository.findByPhonenumber("927249739")).thenReturn(Stream.of(new Adopt(1,"eee","eer").collect(Collectors.toList())));
//    }
//


//public  void get_adoption_requests(){
//        Instant time=2022-03-18T19:06:54.331Z;
//        Instant time2=2022-04-18T19:06:54.331Z;
//        when(adoptRepository.findAllByCreatedOnBetween(time,time2)).thenReturn(Stream.of(new Adopt("2022-04-8","2022-04-8").collect(Collectors.toList())));
//
//assertEquals(2,adoptService.get_adoption_requests(time,time2).size);
//    }
//
}
