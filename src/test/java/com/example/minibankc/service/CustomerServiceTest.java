package com.example.minibankc.service;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.CustomerMapper;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    private CustomerServiceImpl customerService;

    private Customer customer;
    private Account account1;
    private Account account2;
    private AccountTransaction accountTransaction1;
    private AccountTransaction accountTransaction2;
    private Set<Account> accountList=new HashSet<>();
    private Set<AccountTransaction> accountTransactionList=new HashSet<>();
    private final Set<AccountTransaction> accountTransactionList2=new HashSet<>();


    @BeforeEach
    public void setUp() {
        customer=new Customer("Mahdi Mock","Sharifi Mock",accountList);
        customer.setId(1L);
        account1 = new Account(1L,accountTransactionList,customer);
        account1.setId(2L);

        account2 = new Account(2L,accountTransactionList2,customer);
        account2.setId(3L);

        accountList.add(account1);
        accountList.add(account2);

        accountTransaction1 = new AccountTransaction(1,10,1L,account1);
        accountTransaction1.setId(1L);
        accountTransaction2 = new AccountTransaction(2,5,2L,account1);
        accountTransaction2.setId(2L);

        AccountTransaction accountTransaction3 = new AccountTransaction(-1, 1, 3L, account1);
        accountTransaction3.setId(3L);

        accountTransactionList.add(accountTransaction1);
        accountTransactionList.add(accountTransaction2);

        accountTransactionList2.add(accountTransaction3);

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        customerService = new CustomerServiceImpl(customerRepository,customerMapper);
    }

    @Test
    void WhenCustomerNotFound_ThrowsCustomerNotFoundException() {
        Throwable exception = assertThrows(CustomerNotFoundException.class, () ->
                customerService.getCustomerInfo(100000)
        );
        assertEquals("Could not find customer with id: 100000", exception.getMessage());
    }
    @Test
    void WhenCustomerIs1ThenReturnCustomerInfo(){
        CustomerDto customerDto= customerService.getCustomerInfo(1);
        assertEquals(customer.getName(), customerDto.getName());
        assertEquals(customer.getSurname(), customerDto.getSurname());
        assertEquals(customer.getAccounts().size(), customerDto.getAccounts().size());
    }

    @AfterEach
    public void tearDown() {
        account1 = account2 = null;
        accountTransaction1 = accountTransaction2 = null;
        customer = null;
        accountList = null;
        accountTransactionList = null;
    }

}
