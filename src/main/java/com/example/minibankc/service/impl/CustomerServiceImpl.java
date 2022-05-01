package com.example.minibankc.service.impl;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.AccountNotFoundException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.CustomerMapper;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.AccountTransactionRepository;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.CustomerService;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
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

    @Autowired
    private MessageSource messages;

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }
    @Override
    public CustomerDto findOne(Long customerId, String lang) throws CustomerNotFoundException {
        Optional<Customer> customerOptional= customerRepository.findById(customerId);
        customerOptional.ifPresent(customer -> log.debug("#customerOptional with id: "+customerId+" ;"+customer));
        return customerOptional.map(customerMapper::toDto).orElseThrow(
                () -> new CustomerNotFoundException(String.format(messages.getMessage("customer.notfound", null, new Locale(lang)),customerId)));
    }

    /**
     *  This part there was not at assignment, but for manipulating data we need them.
     * @param customerDto the entity to save.
     * @return
     */
    @Override
    public CustomerDto save(CustomerDto customerDto) {
        log.debug("Request to save Customer : {}", customerDto);
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Override
    public Page<CustomerDto> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable).map(customerMapper::toDto);
    }

}
