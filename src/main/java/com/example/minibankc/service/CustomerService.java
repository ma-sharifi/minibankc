package com.example.minibankc.service;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param id the id of the entity.
     * @return the entity.
     */
    CustomerDto findOne(Long id,String lang) throws CustomerNotFoundException;

    /**
     * Save a customer.
     *
     * @param customerDto the entity to save.
     * @return the persisted entity.
     */
    CustomerDto save(CustomerDto customerDto);

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerDto> findAll(Pageable pageable);

}
