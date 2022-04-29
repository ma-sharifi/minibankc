package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.mapper.AccountMapper;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.impl.AccountServiceImpl;
import com.example.minibankc.util.TestUtil;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
class AccountControllerTestIT {

    @SpyBean
    AccountServiceImpl accountService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountMapper accountMapper;


    @Autowired
    MockMvc mockMvc;////It encapsulates all web application beans and makes them available for testing.

    Long initialCredit = 10L;

    @BeforeEach // we don't have to initialize it inside every test.
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountServiceImpl(customerRepository, accountRepository, accountMapper);
        final AccountController controller = new AccountController(accountService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //Given
        Long id = 10L;
        Customer customer = new Customer();
        customer.setName("Mahdi");
        customer.setSurname("Sharifi");

        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(initialCredit);
        account.setId(1L);

        AccountDto accountDto = new AccountDto();
        accountDto.setBalance(initialCredit);
        accountDto.setId(1L);

        //when
        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Mockito.when(accountMapper.toDto(ArgumentMatchers.any(Account.class))).thenReturn(accountDto);

        Mockito.when(accountService.openAccountForExistingCustomer(10, 10)).thenReturn(accountDto);
        Mockito.when(accountService.findOne(1L)).thenReturn(Optional.of(accountDto));
    }

    @Test
    void openAccountWhenCustomerExist() throws Exception {

        //Then
        mockMvc.perform(post("/v1/customers/{customer-id}/accounts", "10").
                header("Initial-Credit", "10")
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(initialCredit)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAccount() throws Exception {
        mockMvc.perform(get("/v1/accounts/{account-id}",1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/v1/accounts/{account-id}",1))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

    }

}
