package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.exception.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 * Used this interface for moving Swagger annotation into this and spare main controller.
 */

public interface ICustomerController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the CustomerDto",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Request.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)})
    @Operation(summary = "Return one customer")
    ResponseEntity<CustomerDto> getCustomerById(
            @Parameter(description = "Id of customer to be searched")
            @PathVariable("customer-id") long customerId
            , @Parameter(description = "Lang for changing message language. lang[en/nl]")
            @RequestParam(required = false, defaultValue = "en") String lang) throws CustomerNotFoundException;

    //*****************This part there was not at assignment, but for manipulating data we need them.*********************
    @Operation(summary = "Ping return pong for checking service")
    ResponseEntity<String> pong();

    @Operation(summary = "Create a new customer and return its URI in location header")
    @ApiResponse(responseCode = "201", description = "Created a customer", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))})
    ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto
            , @RequestParam(required = false, defaultValue = "en") String lang) throws URISyntaxException;


    @Operation(summary = "Return customer list")
    @GetMapping("/customers")
    ResponseEntity<List<CustomerDto>> getAllCustomers(@ParameterObject Pageable pageable);
}
