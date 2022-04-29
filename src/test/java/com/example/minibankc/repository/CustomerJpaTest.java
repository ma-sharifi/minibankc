package com.example.minibankc.repository;

import com.example.minibankc.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
@SpringBootTest
class CustomerJpaTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void shouldSaveAndLoad() {
        Long customerId = transactionTemplate.execute((ts) -> {
            Customer customer =
                    new Customer("Mahdi", "Sharifi", null);
            customerRepository.save(customer);
            return customer.getId();
        });
        transactionTemplate.execute((ts) -> {
            Customer customer = customerRepository.findById(customerId).get();
            assertEquals("Mahdi", customer.getName());
            assertEquals("Sharifi", customer.getSurname());
            assertEquals(new HashSet<>(), customer.getAccounts());
            return null;
        });
    }


}
