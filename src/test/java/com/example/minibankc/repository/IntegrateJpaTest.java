package com.example.minibankc.repository;

import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.ModelExtensionsKt;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class IntegrateJpaTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;


    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void testAllJpas(){
        long referenceNo=123L;
        Long customerId = transactionTemplate.execute((ts) -> {
            Customer customer = new Customer("Mahdi", "Sharifi", null);
            customerRepository.save(customer);

            Account account = new Account(customer);

            AccountTransaction accountTransaction = new AccountTransaction(1);
            accountTransaction.setReferenceNo(referenceNo);
            account.addTransaction(accountTransaction);

            AccountTransaction accountTransaction2 = new AccountTransaction(-1);
            accountTransaction2.setReferenceNo(referenceNo + 1);
            account.addTransaction(accountTransaction2);

            Account accountPersisted = accountRepository.save(account);

            return customer.getId();
        });

        transactionTemplate.execute((ts) -> {
            Customer customer = customerRepository.findById(customerId).get();
            assertEquals("Mahdi", customer.getName());
            assertEquals("Sharifi", customer.getSurname());
            Set<Account> accounts =customer.getAccounts();
            Account account=accounts.stream().findFirst().get();
            assertEquals(1, accounts.size());
            assertEquals(2, account.getAccountTransactions().size());
            return null;
        });
    }
}
