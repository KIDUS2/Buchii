package com.kifiyapro.bunchi.Controller;

import com.kifiyapro.bunchi.Service.CustomerService;
import com.kifiyapro.bunchi.dto.CustomerResponseDto;
import com.kifiyapro.bunchi.dto.ResponseDto;
import com.kifiyapro.bunchi.dto.requestDto.CustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/customer")
@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/add_customer")
    public ResponseEntity<CustomerResponseDto> add_customer(@RequestBody CustomerRequestDto customerRequestDto) {
        return ResponseEntity.ok().body(customerService.add_customer(customerRequestDto));
    }
    @PostMapping("/updateCustomer")
    public ResponseEntity<ResponseDto> updateCustomer(@RequestParam long id, CustomerRequestDto customerRequestDto) {
        return ResponseEntity.ok().body(customerService.updateCustomer(id, customerRequestDto));
    }
}
