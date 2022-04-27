package com.example.minibankc.service.impl;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.CustomerMapper;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.AccountTransactionRepository;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.CustomerService;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, AccountTransactionRepository accountTransactionRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto getCustomerInfo(long customerId) throws CustomerNotFoundException{
        Optional<Customer> customerOptional= customerRepository.findById(customerId);
        customerOptional.ifPresent(customer -> log.debug("#customerOptional with id: "+customerId+" ;"+customer));
        return customerOptional.map(customerMapper::toDto).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }


}
