package com.example.minibankc.controller;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@RestController
@RequestMapping("/v1")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("ping")
    public ResponseEntity<String> pong(){
        return ResponseEntity.ok().body("pong");
    }

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customer-id") long customerId){
        return ResponseEntity.ok().body(customerService.getCustomerInfo(customerId));
    }
}
