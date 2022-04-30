package com.example.minibankc.controller;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@Controller
@Slf4j
@RequestMapping("/v1/ui")
public class CustomerWebController {

    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/international")
    public String getInternationalPage() {
        return "international";
    }
    @RequestMapping("/customers/{customer-id}")
    public String getCustomer(Model model, @PathVariable("customer-id") Long customerId
            ,@RequestParam(required = false , defaultValue = "en") String lang){
        log.debug("#Web request for get customer with id: "+customerId+" ;lang: "+lang);
        CustomerDto customerDto= customerService.findOne(customerId,lang);
        model.addAttribute("customer",customerDto);
        model.addAttribute("accounts",customerDto.getAccounts());
        return "customer/list";
    }
}
