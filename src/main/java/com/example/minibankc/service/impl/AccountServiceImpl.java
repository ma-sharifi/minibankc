package com.example.minibankc.service.impl;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.AccountMapper;
import com.example.minibankc.mapper.CustomerMapper;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.AccountTransactionRepository;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, AccountMapper accountMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Optional<AccountDto> findOne(Long id) {
        log.debug("Request to get Account : {}", id);
        return accountRepository.findById(id).map(accountMapper::toDto);
    }

    @Override
    public AccountDto openAccountForExistingCustomer(long customerId,long initialCredit) throws CustomerNotFoundException{
        log.debug("Request to open an Account. If initialCredit>0 then a transaction will be added : {}", initialCredit);
        Optional<Customer> customerOptional= customerRepository.findById(customerId);
        if(customerOptional.isPresent()) {
            Customer customer=customerOptional.get();
            Account account=new Account(customer);

            if(initialCredit>0) {
                AccountTransaction accountTransaction = new AccountTransaction(initialCredit);
                accountTransaction.setReferenceNo(Instant.now().toEpochMilli() / 1000);
                account.addTransaction(accountTransaction);
            } else account.setBalance(initialCredit);

            accountRepository.save(account);
            return accountMapper.toDto(account);
        }else
            throw new CustomerNotFoundException(customerId);
    }
}
