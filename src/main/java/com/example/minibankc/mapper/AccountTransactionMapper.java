package com.example.minibankc.mapper;

import com.example.minibankc.dto.AccountTransactionDto;
import com.example.minibankc.entity.AccountTransaction;
import org.mapstruct.Mapper;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 * <p>
 * Mapper for the entity {@link AccountTransaction} and its DTO {@link AccountTransactionDto}.
 */
@Mapper(componentModel = "spring")
public interface AccountTransactionMapper extends EntityMapper<AccountTransactionDto, AccountTransaction> {
}
