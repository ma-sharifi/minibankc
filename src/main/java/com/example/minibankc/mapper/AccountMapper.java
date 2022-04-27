package com.example.minibankc.mapper;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.entity.Account;
import org.mapstruct.Mapper;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

/**
 * Mapper for the entity {@link Account} and its DTO {@link AccountDto}.
 */
@Mapper(uses = {AccountTransactionMapper.class}, componentModel = "spring")
public interface AccountMapper extends EntityMapper<AccountDto, Account> {
}
