package com.example.minibankc.repository;

import com.example.minibankc.entity.Account;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccountJpaTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void shouldSaveAndLoad() {
        //The first saves a newly created Account in the database
        Long accountId = transactionTemplate.execute((ts) -> {
            Account account =
                    new Account(1, null, null);
            accountRepository.save(account);
            return account.getId();
        });
        //The second transaction loads the Account and verifies that its fields are properly initialized
        transactionTemplate.execute((ts) -> {
            Account account = accountRepository.findById(accountId).get();
            assertEquals(1, account.getBalance());

            return null;
        });
    }
}
