package com.example.minibankc.repository;

import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccountTransactionRepositoryTest {

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void shouldSaveAndLoad() {
        Long accountTransactionId = transactionTemplate.execute((ts) -> {
            AccountTransaction accountTransaction = new AccountTransaction(1,2, 123L, null);
            accountTransactionRepository.save(accountTransaction);
            return accountTransaction.getId();
        });
        transactionTemplate.execute((ts) -> {
            AccountTransaction accountTransaction = accountTransactionRepository.findById(accountTransactionId).get();
            assertEquals(1, accountTransaction.getAmount());
            assertEquals(2, accountTransaction.getNewBalance());
            assertEquals(123L, accountTransaction.getReferenceNo());
            return null;
        });
    }

    @Test
    void testAccountTransactionAndAccount() {
        long referenceNo=1L;
        Long accountId = transactionTemplate.execute((ts) -> {

            Account account = new Account();
            account.setBalance(1);

            AccountTransaction accountTransaction = new AccountTransaction(1);
            accountTransaction.setReferenceNo(referenceNo);
            account.addTransaction(accountTransaction);

            AccountTransaction accountTransaction2 = new AccountTransaction(-1);
            accountTransaction2.setReferenceNo(referenceNo + 1);
            account.addTransaction(accountTransaction2);
             accountRepository.save(account);
            return account.getId();
        });

        transactionTemplate.execute((ts) -> {
            Account account = accountRepository.findById(accountId).get();
            assertEquals(1, account.getBalance());

            Set<AccountTransaction> accountTransactions = account.getAccountTransactions();
            assertEquals(2, accountTransactions.size());

            return null;
        });
    }
}
