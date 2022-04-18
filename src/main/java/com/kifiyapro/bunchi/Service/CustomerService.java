package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.CustomerResponseDto;
import com.kifiyapro.bunchi.dto.ResponseDto;
import com.kifiyapro.bunchi.dto.requestDto.CustomerRequestDto;
import com.kifiyapro.bunchi.modle.Customer;
import com.kifiyapro.bunchi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;



@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*****************************************
     * add customer and reponse the customerId
     * @Autor kidus
     * @param customerRequestDto
     * @return
     */

    public CustomerResponseDto add_customer(CustomerRequestDto customerRequestDto) {
//        System.out.println("customerRequestDto="+customerRequestDto.getFullname()+"  ---------  "+customerRequestDto.getPhonenumber());
        Customer existingCustomer = customerRepository.findByPhonenumber(customerRequestDto.getPhone());
        if (existingCustomer != null) {
            return new CustomerResponseDto("failed to add the phone number exites ", existingCustomer.getCustomerId());
        } else {
            Customer customer = new Customer();
            customer.setFullname(customerRequestDto.getName());
            customer.setCreatedOn(Instant.now());
            customer.setPhonenumber(customerRequestDto.getPhone());
            System.out.println("customer=" + customer.getFullname() + "  ---------  " + customer.getPhonenumber());

            customerRepository.save(customer);
            return new CustomerResponseDto("success", customer.getCustomerId());

        }
    }


    public ResponseDto updateCustomer(long id, CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findById(id).get();
        customer.setFullname(customerRequestDto.getName());
        customer.setPhonenumber(customerRequestDto.getPhone());
        customer.setUpdatedOn(Instant.now());
        customerRepository.save(customer);
        return new ResponseDto(true, "Updated Successfully");
    }


}




