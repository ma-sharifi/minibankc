package com.example.minibankc.controller;

import com.example.minibankc.IntegrationTest;
import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.BadRequestAlertException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.AccountMapper;
import com.example.minibankc.mapper.CustomerMapper;
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
import static org.hamcrest.Matchers.hasItem;
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
    private CustomerMapper customerMapper;

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
                        post(ENTITY_API_URL_FOR_OPEN_ACCOUNT + "/" + Integer.MAX_VALUE + "/accounts")
                                .header("Initial-Credit", DEFAULT_BALANCE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Could not find customer with id:")))
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
                        post(ENTITY_API_URL_FOR_OPEN_ACCOUNT + "/" + customer.getId() + "/accounts")
                                .header("Initial-Credit", DEFAULT_BALANCE_LESS_THAN_ZERO))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("initial Credit can not be less than zero!", result.getResolvedException().getMessage()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException));

    }


    @Test
    @Transactional
    void createAccountsWithExistingId() throws Exception {
        // Create the Accounts with an existing ID
        account.setId(1L);
        AccountDto accountsDto = accountsMapper.toDto(account);


        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(accountsDto.toJSON()))
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
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(accountsDto.toJSON()))
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
                .perform(get(ENTITY_API_URL))
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
