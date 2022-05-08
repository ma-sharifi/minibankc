package com.example.minibankc.bootstrap;

import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.time.Instant;
import java.util.Arrays;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 * <p>
 * We fill table with some mock data here
 */
@Configuration
@Profile("!prod")
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final Environment environment;

    private final CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository,Environment environment) {
        this.customerRepository = customerRepository;
        this.environment = environment;
    }

    @Override
    public void run(String... args){

        log.info("#mock data is generating.....");
        loadData();
        log.info("#mock data is generated.");
        log.debug("#Currently active profile - " + Arrays.toString(environment.getActiveProfiles()));

    }

    public Customer loadData() {

        AccountTransaction accountTransaction = new AccountTransaction(1);
        accountTransaction.setReferenceNo(Instant.now().toEpochMilli() / 1000);
        Account account = new Account();
        account.addTransaction(accountTransaction);

        AccountTransaction accountTransaction2 = new AccountTransaction(-1);
        accountTransaction2.setReferenceNo(Instant.now().toEpochMilli() / 1000 + 1);
        account.addTransaction(accountTransaction2);

        AccountTransaction accountTransaction3 = new AccountTransaction(3);
        accountTransaction.setReferenceNo(Instant.now().toEpochMilli() / 1000);
        Account account2 = new Account();
        account2.addTransaction(accountTransaction3);

        Customer customer = new Customer();
        customer.setName("Mahdi");
        customer.setSurname("Sharifi");

        customer.addAccount(account);
        customer.addAccount(account2);
        customerRepository.save(customer);
        log.debug("#customer1 : {}", customer);

        Customer customerPaul = new Customer();
        customerPaul.setName("Peter");
        customerPaul.setSurname("Paul");
        customerRepository.save(customerPaul);
        log.debug("#customer2 : {}", customerPaul);

        return customer;
    }
}
