package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.exception.AccountNotFoundException;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.util.PaginationUtil;
import com.example.minibankc.util.apects.Loggable;
import com.example.minibankc.util.apects.RateController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */

public interface IAccountController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created an account for current customer", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Request. If initialCredit be less than zero it throws 400.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found for creating an account for it", content = @Content)})
    @Operation(summary = "Open an account for existing customer and return its URI in location header")
    ResponseEntity<AccountDto> openAccountForExistingCustomer(
            @Parameter(description = "Id of customer to open an account for it")
            @PathVariable("customer-id") long customerId,
            @Parameter(description = "Initial-Credit must be greater than zero. I put it in header for filters. We can access headers on filter.")
            @RequestHeader("Initial-Credit") long initialCredit,
            @Parameter(description = "Because post is not idempotent ,used X-Request-Id for avoid repeating request by user. Client must change it in evey request. example: X-Request-Id: ABCD")
            @RequestHeader(value = "X-Request-Id" ,defaultValue = "ABCD") String requestId,
            @Parameter(description = "Lang for changing message language. lang[en/nl]")
            @RequestParam(required = false, defaultValue = "en") String lang
    ) throws CustomerNotFoundException;
    //*****************This part there was not at assignment, but for manipulating data we need them.*********************

    ResponseEntity<AccountDto> getAccount(@PathVariable("account-id") Long id
            , @RequestParam(required = false, defaultValue = "en") String lang) throws AccountNotFoundException;

    ResponseEntity<List<AccountDto>> getAllAccounts(@ParameterObject Pageable pageable
            , @RequestParam(required = false, defaultValue = "en") String lang);
}
