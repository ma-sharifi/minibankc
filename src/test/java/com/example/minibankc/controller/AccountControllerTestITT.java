package com.example.minibankc.controller;

import com.example.minibankc.IntegrationTest;
import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.BadRequestAlertException;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static com.example.minibankc.util.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private static final Long DEFAULT_BALANCE = 1L;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);

    private static final String ENTITY_API_URL = "/v1/accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AccountRepository accountsRepository;

    @Autowired
    private AccountMapper accountsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Account account;

    /**
     * Create an entity for this test.
     *
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
    void createAccounts() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();
        // Create the Accounts
        AccountDto accountsDto = accountsMapper.toDto(account);
        restAccountsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDto)))
                .andExpect(status().isCreated());

        // Validate the Accounts in the database
        List<Account> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate + 1);
        Account testAccount = accountsList.get(accountsList.size() - 1);
        assertThat(testAccount.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void createAccountsWithExistingId() throws Exception {
        // Create the Accounts with an existing ID
        account.setId(1L);
        AccountDto accountsDto = accountsMapper.toDto(account);


        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("A new customer cannot already have an ID", result.getResolvedException().getMessage()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException));
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsRepository.findAll().size();
        // set the field null
        account.setBalance(-1);

        // Create the Accounts, which fails.
        AccountDto accountsDto = accountsMapper.toDto(account);

        restAccountsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDto)))
                .andExpect(status().isBadRequest());

        List<Account> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(account);

        // Get all the accountsList
        restAccountsMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").value(hasItem(account.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].balance").value(hasItem((DEFAULT_BALANCE))));
    }

    @Test
    @Transactional
    void getAccounts() throws Exception {
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
    void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }


//    @SpyBean
//    AccountServiceImpl accountService;
//    @Mock
//    CustomerRepository customerRepository;
//    @Mock
//    AccountRepository accountRepository;
//    @Mock
//    AccountMapper accountMapper;
//
//    @Autowired
//    MockMvc mockMvc;////It encapsulates all web application beans and makes them available for testing.
//
//    Long initialCredit = 10L;
//
//    @BeforeEach // we don't have to initialize it inside every test.
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        accountService = new AccountServiceImpl(customerRepository, accountRepository, accountMapper);
//        final AccountController controller = new AccountController(accountService);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//
//        //Given
//        Long id = 10L;
//        Customer customer = new Customer();
//        customer.setName("Mahdi");
//        customer.setSurname("Sharifi");
//
//        Account account = new Account();
//        account.setCustomer(customer);
//        account.setBalance(initialCredit);
//        account.setId(1L);
//
//        AccountDto accountDto = new AccountDto();
//        accountDto.setBalance(initialCredit);
//        accountDto.setId(1L);
//
//        //when
//        Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
//        Mockito.when(accountRepository.save(account)).thenReturn(account);
//
//        Mockito.when(accountMapper.toDto(ArgumentMatchers.any(Account.class))).thenReturn(accountDto);
//
//        Mockito.when(accountService.openAccountForExistingCustomer(10, 10)).thenReturn(accountDto);
//        Mockito.when(accountService.findOne(1L)).thenReturn(Optional.of(accountDto));
//    }
//
//    @Test
//    void openAccountWhenCustomerExist() throws Exception {
//
//        //Then
//        mockMvc.perform(post("/v1/customers/{customer-id}/accounts", "10").
//                header("Initial-Credit", "10")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(TestUtil.convertObjectToJsonBytes(initialCredit)))
//                .andExpect(status().isCreated());
//    }

}
