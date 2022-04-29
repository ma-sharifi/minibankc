package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.AccountMapper;
import com.example.minibankc.mapper.CustomerMapper;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.impl.AccountServiceImpl;
import com.example.minibankc.service.impl.CustomerServiceImpl;
import com.example.minibankc.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/29/22
 * IT without explicitly starting a Servlet container
 */
@AutoConfigureMockMvc
class CustomerControllerTestIT {

    @SpyBean
    CustomerServiceImpl customerService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;

    @Autowired
    MockMvc mockMvc;////It encapsulates all web application beans and makes them available for testing.

    Long initialCredit = 10L;

    @BeforeEach // we don't have to initialize it inside every test.
    void setUp() {
        MockitoAnnotations.initMocks(this);

        //Given
        Long id = 10L;
        Customer customer = new Customer();
        customer.setName("Mahdi");
        customer.setSurname("Sharifi");


        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(initialCredit);
        account.setId(1L);
        customer.setAccounts(Set.of(account));

        AccountDto accountDto = new AccountDto();
        accountDto.setBalance(initialCredit);
        accountDto.setId(1L);

        CustomerDto dto=new CustomerDto();
        dto.setSurname("Sharifi");
        dto.setName("Mahdi");
        dto.setAccounts(Set.of(accountDto));

        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
        final CustomerController controller = new CustomerController(customerService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //when
        Mockito.when(customerMapper.toDto(ArgumentMatchers.any(Customer.class))).thenReturn(dto);
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(customerService.getCustomerInfo(1)).thenReturn(dto);
    }

    @Test
    void openAccountWhenCustomerExist() throws Exception {

        //Then
        mockMvc.perform(get("/v1/customers/{customer-id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
