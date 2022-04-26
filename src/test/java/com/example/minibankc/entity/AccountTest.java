package com.example.minibankc.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
public class AccountTest {
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void getBalance() {
        Long balance = 2L;
        account.setBalance(balance );
        assertEquals(balance, account.getBalance());
    }

    @Test
    void getCustomer() {
        Customer customer = new Customer();
        customer.setName("Mahdi");
        account.setCustomer(customer);
        assertEquals("Mahdi", account.getCustomer().getName());
        assertNotNull(account.getCustomer());
    }
}
