package com.example.minibankc.controller;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@Controller
public class CustomerWebController {

    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/v1/c")
    public String getCustomer(Model model){
        CustomerDto customerDto= customerService.findOne(1L);
        model.addAttribute("customer",customerDto);
        return "customer/list";
    }
}
