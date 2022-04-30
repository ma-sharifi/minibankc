package com.example.minibankc.controller;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.service.CustomerService;
import com.example.minibankc.util.HeaderUtil;
import com.example.minibankc.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
@Slf4j
public class CustomerController {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private MessageSource messages;

    private final CustomerService customerService;

    private static final String ENTITY_NAME = "customer";

    @Value("${spring.application.name}")
    private String applicationName;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("ping")
    public ResponseEntity<String> pong(){
        return ResponseEntity.ok().body("pong");
    }

    /**
     * {@code GET  /customers/:customer-id} : get the "customer-id" customer.
     *
     * @param customerId the id of the customerDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerDto, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customer-id") long customerId
            ,@RequestParam(required = false , defaultValue = "en") String lang) throws CustomerNotFoundException {
        return ResponseEntity.ok().body(customerService.findOne(customerId,lang));
    }

    //*****************This part there was not at assignment, but for manipulating data we need them.*********************
    /**
     * {@code POST  /customers} : Create a new customer.
     *
     * @param customerDto the customerDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerDto, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto
            ,@RequestParam(required = false , defaultValue = "en") String lang) throws URISyntaxException {
        if(customerDto==null)  throw new CustomerNotFoundException(0L);
        log.debug("REST request to save Customer : {}", customerDto.toJSON());
        if (customerDto.getId() != null) {
            throw new BadRequestAlertException(String.format(messages.getMessage("customer.alreadyhaveid.error.message", null, new Locale(lang))), ENTITY_NAME, "customer.alreadyhaveid.error.message");
        }
        CustomerDto result = customerService.save(customerDto);
        return ResponseEntity
                .created(new URI("http://localhost:"+serverPort+"/v1/customers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Customers");
        Page<CustomerDto> page = customerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
