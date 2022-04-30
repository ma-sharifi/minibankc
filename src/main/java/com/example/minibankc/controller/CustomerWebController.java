package com.example.minibankc.controller;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.service.CustomerService;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/international")
    public String getInternationalPage() {
        return "international";
    }
    @RequestMapping("/v1/c")
    public String getCustomer(Model model
            ,@RequestParam(required = false , defaultValue = "en") String lang){
        CustomerDto customerDto= customerService.findOne(1L,lang);
        model.addAttribute("customer",customerDto);
        return "customer/list";
    }
}
