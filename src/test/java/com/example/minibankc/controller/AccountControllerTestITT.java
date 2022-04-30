package com.example.minibankc.controller;

import com.example.minibankc.IntegrationTest;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.AccountNotFoundException;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.AccountMapper;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/29/22
 * IT without explicitly starting a Servlet container
 */

/**
 * Integration tests for the {@link AccountController} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
class AccountControllerTestITT {

    private static final Long DEFAULT_BALANCE = 5L;
    private static final Long DEFAULT_BALANCE_LESS_THAN_ZERO = -6L;

    private static final String ENTITY_API_URL = "/v1/accounts";
    private static final String ENTITY_API_URL_FOR_OPEN_ACCOUNT = "/v1/customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AccountRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountMapper accountsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Account account;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Account createEntity(EntityManager em) {
        Account account = new Account();
        account.setBalance(DEFAULT_BALANCE);
        return account;
    }

    @BeforeEach
    public void initTest() {
        account = createEntity(em);
    }

    @Test
    @Transactional
    void openAccountForExistingCustomerWithInitialCreditMoreThanZero() throws Exception {
        Customer customer = new Customer();
        customer.setName("Mahdi");
        customer.setSurname("Sharifi");

        customerRepository.saveAndFlush(customer); //Save Customer;

        int accountCounter = customer.getAccounts().size();

        int databaseSizeBeforeCreate = accountsRepository.findAll().size();
        // Open an Account for existing customer
        restAccountsMockMvc
                .perform(
                        post(ENTITY_API_URL_FOR_OPEN_ACCOUNT + "/" + customer.getId() + "/accounts")
                                .header("Initial-Credit", DEFAULT_BALANCE))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(DEFAULT_BALANCE));

        // Validate the Accounts in the database
        List<Account> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate + 1);
        Account testAccount = accountsList.get(accountsList.size() - 1);
        assertThat(testAccount.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void openAccountForNonExistingCustomer() throws Exception {
        restAccountsMockMvc
                .perform(
                        post(ENTITY_API_URL_FOR_OPEN_ACCOUNT + "/" + Integer.MAX_VALUE + "/accounts?lang=en")
                                .header("Initial-Credit", DEFAULT_BALANCE))
                .andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Could not find customer with id:")))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException));

    }

    @Test
    @Transactional
    void openAccountForExistingCustomerWithInitialCreditLessThanZero() throws Exception {
        Customer customer = new Customer();
        customer.setName("Mahdi");
        customer.setSurname("Sharifi");

        customerRepository.saveAndFlush(customer); //Save Customer;

        restAccountsMockMvc
                .perform(
                        post(ENTITY_API_URL_FOR_OPEN_ACCOUNT + "/" + customer.getId() + "/accounts?lang=en")
                                .header("Initial-Credit", DEFAULT_BALANCE_LESS_THAN_ZERO))
                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertEquals("initial Credit can not be less than zero!", result.getResolvedException().getMessage()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException));
    }

    @Test
    @Transactional
    void getAllAccounts() throws Exception {
        // Initialize the database
        int count=(int) accountsRepository.count();
        accountsRepository.saveAndFlush(account);
        // Get all the accountsList
        restAccountsMockMvc
                .perform(get(ENTITY_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").value(hasItem(account.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(count+1)));
    }

    @Test
    @Transactional
    void getAccount() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(account);

        // Get the accounts
        restAccountsMockMvc
                .perform(get(ENTITY_API_URL_ID, account.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(account.getId().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value((DEFAULT_BALANCE)));
    }

    @Test
    @Transactional
    void getNonExistingAccount() throws Exception {
        restAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AccountNotFoundException));

    }

}
