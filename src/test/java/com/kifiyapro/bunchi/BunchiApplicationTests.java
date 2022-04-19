package com.kifiyapro.bunchi;

import com.kifiyapro.bunchi.Service.AdoptService;
import com.kifiyapro.bunchi.Service.CustomerService;
import com.kifiyapro.bunchi.modle.Adopt;
import com.kifiyapro.bunchi.modle.Customer;
import com.kifiyapro.bunchi.modle.Pet;
import com.kifiyapro.bunchi.repository.AdoptRepository;
import com.kifiyapro.bunchi.repository.CustomerRepository;
import com.kifiyapro.bunchi.repository.PetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.Rollback;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
//@EnableConfigurationProperties({FileStorageProperties.class})
@EnableSwagger2
@EnableScheduling
class BunchiApplicationTests {

    Long adopt_id;
    static Long pet_id;
    static Long customer_id;
    @Autowired
    private CustomerService customerService;
    private AdoptService adoptService;

//    @MockBean
//    private CustomerRepository customerRepository;
//    private AdoptRepository adoptRepository;

    @Test
    void contextLoads() {

    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private AdoptRepository adoptRepository;


    @Test
    @Order(1)
    @Rollback(value = false)

    public void saveCustomerTest() {


        Customer customer = new Customer();
        customer.setPhonenumber("lioyq");
        customer.setFullname("kifiya");
        customer.setCreatedOn(Instant.now());

        customerRepository.save(customer);
        customer_id=customer.getCustomerId();
        Assertions.assertThat(customer.getCustomerId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)

    public void savePetTest() {
        Pet pet = new Pet();
        pet.setType("dog");
        pet.setSize("small");
        pet.setAge("Baby");
        pet.setGender("male");
        pet.setGoodWithChildren(true);
        pet.setPhoto("lion-theme-wx2.jpg");

        petRepository.save(pet);
        pet_id=pet.getPetId();
        Assertions.assertThat(pet.getPetId()).isGreaterThan(0);
    }
    @Test
    @Order(3)
    public void getListOfPetTest(){

        List<Pet> pets = petRepository.findAll();

        Assertions.assertThat(pets.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void saveAdoptTest() {
        Long customer_id = customerRepository.findAll().get(0).getCustomerId();
        Long pet_id=petRepository.findAll().get(0).getPetId();

        Optional<Customer> customer = customerRepository.findById(customer_id);
        Optional<Pet> pet = petRepository.findById(pet_id);
        Adopt adopt =new Adopt();
//        System.out.println("*************newwww"+adopt.getCustomer().getCustomerId());
        adopt.setCustomer(customer.get());
//        System.out.println("**********************"+adopt.getPet().getPetId());
        adopt.setPet(pet.get());
        adopt.setCreatedOn(new Date());
        adoptRepository.save(adopt);
       Assertions.assertThat(adopt.getAdoptionId()).isGreaterThan(0);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void getListOfAdoptTest(){

        List<Adopt> adopts = adoptRepository.findAll();

        Assertions.assertThat(adopts.size()).isGreaterThan(0);

    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void getListOfAdoptByweekTest(){

        List<Adopt> adopts = adoptRepository.findAll();

        Assertions.assertThat(adopts.size()).isGreaterThan(0);

    }

}
