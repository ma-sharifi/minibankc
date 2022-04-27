package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.service.AccountService;
import com.example.minibankc.service.CustomerService;
import com.example.minibankc.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class AccountController {

    @Value("${server.port}")
    private int serverPort;

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * {@code GET  /accounts/:id} : get the "id" accounts.
     *
     * @param id the id of the accountsDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountsDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> getAccounts(@PathVariable Long id) {
        log.debug("REST request to get Accounts : {}", id);
        Optional<AccountDto> accountsDto = accountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountsDto);
    }


    @PostMapping("/customers/{customer-id}/accounts")
    public ResponseEntity<AccountDto> getCustomerById(@PathVariable("customer-id") long customerId
            , @RequestHeader("Initial-Credit") long initialCredit){
         log.debug("REST request to create account for customer : {}", customerId);
        AccountDto accountDto=accountService.openAccountForExistingCustomer(customerId,initialCredit);
        URI location=URI.create("http://localhost:"+serverPort+"/v1/accounts/"+accountDto.getId());
        return ResponseEntity.created(location).body(accountDto);
    }
}
