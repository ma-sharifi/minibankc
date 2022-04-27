package com.example.minibankc.service;

import com.example.minibankc.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

@SpringBootTest
public class CustomerServiceTest {

    CustomerService customerService;

    @BeforeEach
    void setUp() {

        account = new Account();
    }
}
