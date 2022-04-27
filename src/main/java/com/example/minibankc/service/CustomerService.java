package com.example.minibankc.service;

import com.example.minibankc.dto.CustomerDto;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */

public interface CustomerService {
    CustomerDto getCustomer(long customerId);
}
