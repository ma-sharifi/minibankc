package com.example.minibankc.bootstrap;

import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.AccountTransactionRepository;
import com.example.minibankc.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    public DataLoader(CustomerRepository customerRepository, AccountRepository accountRepository, AccountTransactionRepository accountTransactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("#mock data is generating.....");
        loadData();
        log.info("#mock data is generated.");
    }

    public Customer loadData() {
        Customer customer = new Customer();
        customer.setName("Mahdi");
        customer.setSurname("Sharifi");
        Customer savedCustomer = customerRepository.save(customer);

        Customer customerPaul = new Customer();
        customerPaul.setName("Paul");
        customerPaul.setSurname("Peter");
        Customer savedCustomerPaul = customerRepository.save(customerPaul);

        Account account = new Account(savedCustomer);
        Account account2 = new Account(savedCustomer);

        AccountTransaction accountTransaction = new AccountTransaction(1);
        accountTransaction.setReferenceNo(Instant.now().toEpochMilli() / 1000);
        account.addTransaction(accountTransaction);

        AccountTransaction accountTransaction2 = new AccountTransaction(-1);
        accountTransaction2.setReferenceNo(Instant.now().toEpochMilli() / 1000 + 1);
        account.addTransaction(accountTransaction2);

        AccountTransaction accountTransaction3 = new AccountTransaction(3);
        accountTransaction.setReferenceNo(Instant.now().toEpochMilli() / 1000);
        account2.addTransaction(accountTransaction3);

        accountRepository.save(account);
        log.debug("#account : {}", account);
        accountRepository.save(account2);
        log.debug("#account2 : {}", account2);
        return customer;
    }
}
