package com.example.minibankc.mapper;


import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Customer;
import org.mapstruct.Mapper;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDto}.
 */
@Mapper(uses = {AccountMapper.class, AccountTransactionMapper.class}, componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {
}
