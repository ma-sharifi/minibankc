package com.example.minibankc.entity;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CustomerJpaTest {
    @Autowired
    private CustomerRepository customerRepository;

}
