package com.example.minibankc.service;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.mapper.CustomerMapper;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

@ExtendWith(MockitoExtension.class)
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
    private AccountTransaction accountTransaction3;
    private Set<Account> accountList=new HashSet<>();
    private Set<AccountTransaction> accountTransactionList=new HashSet<>();
    private Set<AccountTransaction> accountTransactionList2=new HashSet<>();

    @Test
    void serviceTest(){
        CustomerDto customerDto= customerService.getCustomerInfo(1);
        assertEquals(customer.getName(), customerDto.getName());
        assertEquals(customer.getSurname(), customerDto.getSurname());
        assertEquals(customer.getAccounts().size(), customerDto.getAccounts().size());
    }

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

        accountTransaction3 = new AccountTransaction(-1,1,3L,account1);
        accountTransaction3.setId(3L);

        accountTransactionList.add(accountTransaction1);
        accountTransactionList.add(accountTransaction2);

        accountTransactionList2.add(accountTransaction3);

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        System.out.println("#customer: "+customer);
        System.out.println("#customer2: "+customerRepository.findById(1L).get());
        customerService = new CustomerServiceImpl(customerRepository,null,null,customerMapper);
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
