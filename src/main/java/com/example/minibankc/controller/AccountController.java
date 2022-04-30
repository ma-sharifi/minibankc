package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.service.AccountService;
import com.example.minibankc.service.CustomerService;
import com.example.minibankc.util.PaginationUtil;
import com.example.minibankc.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
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

    @Value("${spring.application.name}")
    private String applicationName;


    private static final String ENTITY_NAME = "account";

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
    @GetMapping("/accounts/{account-id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("account-id") Long id) {
        log.debug("REST request to get Accounts : {}", id);
        Optional<AccountDto> accountsDto = accountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountsDto);
    }

    /**
     * create and account for customer if customer exist.
     * if blanace is grater than 0 a transaction create as well.
     * @param customerId
     * @param initialCredit
     * @return
     */
    @PostMapping("/customers/{customer-id}/accounts")
    public ResponseEntity<AccountDto> openAccountForExistingCustomer(@PathVariable("customer-id") long customerId
            , @RequestHeader("Initial-Credit") long initialCredit){
        log.debug("REST request to create account for customer : {}", customerId);
        if(initialCredit<0)
            throw new BadRequestAlertException("initial Credit can not be less than zero!", ENTITY_NAME, "creditlessthanzero");

        AccountDto accountDto=accountService.openAccountForExistingCustomer(customerId,initialCredit);
        URI location=URI.create("http://localhost:"+serverPort+"/v1/accounts/"+accountDto.getId());
        return ResponseEntity.created(location).body(accountDto);
    }

    /**
     * {@code GET  /accounts} : get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accounts in body.
     */
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Accounts");
        Page<AccountDto> page = accountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
