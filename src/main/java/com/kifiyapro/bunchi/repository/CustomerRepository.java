package com.kifiyapro.bunchi.repository;

import com.kifiyapro.bunchi.modle.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


//    Customer findByName(String name);
//    List<Customer> findByCustomer_id(Long id);
    Customer findByPhonenumber(String phoneNo);
//    List<Customer> findCustomerByPet(Customer customer);
}