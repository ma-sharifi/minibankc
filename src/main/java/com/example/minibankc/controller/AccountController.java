package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.service.AccountService;
import com.example.minibankc.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@RestController
@RequestMapping("/v1/customers")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("{customer-id}/accounts")
    public ResponseEntity<AccountDto> getCustomerById(@PathVariable("customer-id") long customerId
            , @RequestHeader("Initial-Credit") long initialCredit, HttpServletRequest httpServletRequest){
         log.debug("REST request to create account for customer : {}", customerId);

        String path = httpServletRequest.getRequestURI();
        log.debug("getServletPath: "+httpServletRequest.getServletPath());
        log.debug("getPathInfo: "+httpServletRequest.getPathInfo());
        httpServletRequest.getMethod();
        log.debug("path: "+path);///v1/customers/1/accounts
        AccountDto accountDto=accountService.openAccountForExistingCustomer(customerId,initialCredit);
        URI location=URI.create("http://"+accountDto.getId());
        return ResponseEntity.created(location).body(accountDto);
    }
}
