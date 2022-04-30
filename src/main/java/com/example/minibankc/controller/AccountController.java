package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.exception.AccountNotFoundException;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.service.AccountService;
import com.example.minibankc.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Description;
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
     * create and account for customer if customer exist.
     * if balance is grater than 0 a transaction create as well.
     *
     * @param customerId
     * @param initialCredit
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created an account for current customer",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Request. If initialCredit be less than zero it throws 400.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found for creating an account for it", content = @Content) })
    @Operation(summary = "Open an account for existing customer")
    @PostMapping("/customers/{customer-id}/accounts")
    public ResponseEntity<AccountDto> openAccountForExistingCustomer(
            @Parameter(description = "Id of customer to open an account for it")
            @PathVariable("customer-id") long customerId
            ,@Parameter(description = "Initial-Credit must be greater than zero.")
            @RequestHeader("Initial-Credit") long initialCredit
            ,@Parameter(description = "Lang for changing message language. lang[en/nl]")
            @RequestParam(required = false , defaultValue = "en") String lang

    ) throws CustomerNotFoundException {
        log.debug("REST request to create account for customer : {}", customerId);
        if (initialCredit < 0)
            throw new BadRequestAlertException(String.format(messages.getMessage("account.initialicreditlessthanzero.error.message", null, new Locale(lang))), ENTITY_NAME, "account.initialicreditlessthanzero.error.message");

        AccountDto accountDto = accountService.openAccountForExistingCustomer(customerId, initialCredit,lang);
        URI location = URI.create("http://localhost:" + serverPort + "/v1/accounts/" + accountDto.getId());
        return ResponseEntity.created(location).body(accountDto);
    }
    //*****************This part there was not at assignment, but for manipulating data we need them.*********************

    /**
     * {@code GET  /account/:id} : get the "id" account.
     *
     * @param id the id of the accountDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounts/{account-id}")
    @Operation(summary = "Get Account details")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("account-id") Long id
            ,@RequestParam(required = false , defaultValue = "en") String lang) throws AccountNotFoundException {
        log.debug("REST request to get Account : {}", id);
        LocaleContextHolder.setDefaultLocale(Locale.forLanguageTag("nl"));
        return ResponseEntity.ok().body(accountService.findOne(id,lang));
    }
    /**
     * {@code GET  /account} : get all the account.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of account in body.
     */
    @Operation(summary = "Get all accounts")
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts(@ParameterObject Pageable pageable
            ,@RequestParam(required = false , defaultValue = "en") String lang
    ) {
        log.debug("REST request to get a page of Accounts");
        Page<AccountDto> page = accountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
