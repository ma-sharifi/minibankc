package com.example.minibankc.service;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.exception.CustomerNotFoundException;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
public interface AccountService {
    AccountDto openAccountForExistingCustomer(long customerId, long initialCredit) throws CustomerNotFoundException;
}
