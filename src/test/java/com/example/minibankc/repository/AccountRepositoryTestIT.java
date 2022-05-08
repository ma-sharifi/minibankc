package com.example.minibankc.repository;
import com.example.minibankc.entity.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/29/22
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTestIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void shouldSaveNewAccountForExistingCustomer(){
        Account account = new Account();
        account.setBalance(1000L);
        Account accountPersisted=entityManager.merge(account);

        Account accountFound = accountRepository.getById(accountPersisted.getId());

        assertNotNull(accountFound);
        assertThat(accountPersisted.getBalance()).isEqualTo(accountFound.getBalance());
    }
}
