package com.example.minibankc.service.impl;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.exception.AccountNotFoundException;
import com.example.minibankc.exception.CustomerNotFoundException;
import com.example.minibankc.mapper.AccountMapper;
import com.example.minibankc.repository.AccountRepository;
import com.example.minibankc.repository.CustomerRepository;
import com.example.minibankc.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 * Service of account entity
 */
@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    @Autowired
    private MessageSource messages;

    public AccountServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, AccountMapper accountMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDto findOne(Long id, String lang) throws AccountNotFoundException {
        log.debug("Request to get Account : {}", id);
        Optional<Account> accountOptional = accountRepository.findById(id);
        return accountOptional.map(accountMapper::toDto).orElseThrow(() ->
                new AccountNotFoundException(String.format(messages.getMessage("account.notfound", null, new Locale(lang)), id)));
    }

    @Override
    public Page<AccountDto> findAll(Pageable pageable) {
        log.debug("Request to get all Account");
        return accountRepository.findAll(pageable).map(accountMapper::toDto);
    }

    @Override
    public AccountDto openAccountForExistingCustomer(long customerId, long initialCredit, String lang) throws CustomerNotFoundException {
        log.debug("Request to open an Account. If initialCredit>0 then a transaction will be added : {}", initialCredit);
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            Account account = new Account(customer);

            if (initialCredit > 0) {
                AccountTransaction accountTransaction = new AccountTransaction(initialCredit);
                accountTransaction.setReferenceNo(Instant.now().toEpochMilli() / 1000);
                account.addTransaction(accountTransaction);
            } else account.setBalance(initialCredit);

            accountRepository.save(account);
            return accountMapper.toDto(account);
        } else
            throw new CustomerNotFoundException(String.format(messages.getMessage("customer.notfound", null, new Locale(lang)), customerId));

    }

}
