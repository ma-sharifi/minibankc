package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.service.CustomerService;
import com.example.minibankc.util.HeaderUtil;
import com.example.minibankc.util.PaginationUtil;
import com.example.minibankc.util.filter.Loggable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@RestController
@RequestMapping("/v1")
@Tag(name = "customer-controller for handling customers", description = "Get/Create the customer")
@Slf4j
public class CustomerController {

    private static final String ENTITY_NAME = "customer";
    private final CustomerService customerService;
    @Value("${server.port}")
    private int serverPort;
    @Autowired
    private MessageSource messages;
    @Value("${spring.application.name}")
    private String applicationName;


    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;

    }

    /**
     * {@code GET  /customers/:customer-id} : get the "customer-id" customer.
     *
     * @param customerId the id of the customerDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerDto, or with status {@code 404 (Not Found)}.
     */
    @Loggable
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the CustomerDto",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Request.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)})
    @Operation(summary = "Return one customer")
    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<CustomerDto> getCustomerById(
            @Parameter(description = "Id of customer to be searched")
            @PathVariable("customer-id") long customerId
            , @Parameter(description = "Lang for changing message language. lang[en/nl]")
            @RequestParam(required = false, defaultValue = "en") String lang) throws CustomerNotFoundException {
        return ResponseEntity.ok().body(customerService.findOne(customerId, lang));
    }

    //*****************This part there was not at assignment, but for manipulating data we need them.*********************
    @Operation(summary = "Ping return pong for checking service")
    @GetMapping("ping")
    public ResponseEntity<String> pong() {
        return ResponseEntity.ok().body("pong");
    }

    /**
     * {@code POST  /customers} : Create a new customer.
     *
     * @param customerDto the customerDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerDto, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Operation(summary = "Create a new customer and return its URI in location header")
    @ApiResponse(responseCode = "201", description = "Created a customer", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))})
    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto
            , @RequestParam(required = false, defaultValue = "en") String lang) throws URISyntaxException {
        if (customerDto == null) throw new CustomerNotFoundException(0L);
        log.debug("REST request to save Customer : {}", customerDto.toJSON());
        if (customerDto.getId() != null) {
            throw new BadRequestAlertException(String.format(messages.getMessage("customer.alreadyhaveid", null, new Locale(lang))), ENTITY_NAME, "customer.alreadyhaveid");
        }
        CustomerDto result = customerService.save(customerDto);
        return ResponseEntity
                .created(new URI("http://localhost:" + serverPort + "/v1/customers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @Operation(summary = "Return customer list")
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Customers");
        Page<CustomerDto> page = customerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
