package com.example.minibankc.service.impl;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.AccountTransactionRepository;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, AccountTransactionRepository accountTransactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @Override
    public CustomerDto getCustomer(long customerId) {
        Optional<Customer> customerOptional= customerRepository.findById(customerId);
        return null;
    }
}
