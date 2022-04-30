package com.example.minibankc.service;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.exception.AccountNotFoundException;
import com.example.minibankc.exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
public interface AccountService {

    AccountDto openAccountForExistingCustomer(long customerId, long initialCredit, String lang) throws CustomerNotFoundException;

    /**
     * Get the "id" accounts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    AccountDto findOne(Long id, String lang);

    /**
     * Get all the accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountDto> findAll(Pageable pageable);

}
