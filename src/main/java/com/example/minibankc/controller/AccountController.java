package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.exception.AccountNotFoundException;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.service.AccountService;
import com.example.minibankc.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22ß
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class AccountController {

    private static final String ENTITY_NAME = "account";
    private final AccountService accountService;
    @Value("${server.port}")
    private int serverPort;
    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private MessageSource messages;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * {@code GET  /account/:id} : get the "id" account.
     *
     * @param id the id of the accountDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounts/{account-id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("account-id") Long id
            ,@RequestParam(required = false , defaultValue = "en") String lang) throws AccountNotFoundException {
        log.debug("REST request to get Account : {}", id);
        LocaleContextHolder.setDefaultLocale(Locale.forLanguageTag("nl"));
        return ResponseEntity.ok().body(accountService.findOne(id,lang));
    }

    /**
     * create and account for customer if customer exist.
     * if blanace is grater than 0 a transaction create as well.
     *
     * @param customerId
     * @param initialCredit
     * @return
     */
    @PostMapping("/customers/{customer-id}/accounts")
    public ResponseEntity<AccountDto> openAccountForExistingCustomer(@PathVariable("customer-id") long customerId
            , @RequestHeader("Initial-Credit") long initialCredit
            ,@DefaultValue("en")  @RequestParam(required = false) String lang

    ) throws CustomerNotFoundException {
        log.debug("REST request to create account for customer : {}", customerId);
        if (initialCredit < 0)
            throw new BadRequestAlertException(String.format(messages.getMessage("account.initialicreditlessthanzero.error.message", null, new Locale(lang))), ENTITY_NAME, "account.initialicreditlessthanzero.error.message");

        AccountDto accountDto = accountService.openAccountForExistingCustomer(customerId, initialCredit,lang);
        URI location = URI.create("http://localhost:" + serverPort + "/v1/accounts/" + accountDto.getId());
        return ResponseEntity.created(location).body(accountDto);
    }

    /**
     * {@code GET  /account} : get all the account.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of account in body.
     */
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts(@ParameterObject Pageable pageable
            ,@DefaultValue("en")  @RequestParam(required = false) String lang
    ) {
        log.debug("REST request to get a page of Accounts");
        Page<AccountDto> page = accountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
