package com.example.minibankc.service;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.exception.CustomerNotFoundException;

import java.util.Optional;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
public interface CustomerService {
    /**
     * Get the "id" customer.
     *
     * @param id the id of the customer entity.
     * @return the CustomerDto.
     */

    CustomerDto getCustomerInfo(long id) throws CustomerNotFoundException;

}
