package com.example.minibankc.mapper;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.AccountTransactionDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@SpringBootTest
class AccountMapperTest {

    @Autowired
    private AccountMapper mapper;

    @Test
    void givenEntityToDto_whenMaps_thenCorrect() {
        Account entity= new Account();
        entity.setBalance(1L);
        entity.setId(2L);
        AccountDto dto = mapper.toDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getBalance(), dto.getBalance());
    }
    @Test
    void givenDtoToEntity_whenMaps_thenCorrect() {
        AccountDto dto = new AccountDto();
        dto.setBalance(1L);
        dto.setId(2L);
        Account entity = mapper.toEntity(dto);
        
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBalance(), entity.getBalance());
    }

    @Test
    void  givenDtoToEntity_whenMaps_thenCorrect_allDependency(){
        Account entityAccount;
        AccountTransaction accountTransaction1;
        Set<AccountTransaction> accountTransactionList=new HashSet<>();

        entityAccount = new Account(1L,accountTransactionList,null);
        entityAccount.setId(1L);
        entityAccount.setCreatedAt(new Date());

        accountTransaction1 = new AccountTransaction(1,10,1L,entityAccount);
        accountTransaction1.setId(1L);

        accountTransactionList.add(accountTransaction1);

        AccountDto accountDto = mapper.toDto(entityAccount);

        assertEquals(entityAccount.getId(), accountDto.getId());
        assertEquals(entityAccount.getBalance(), accountDto.getBalance());
        assertEquals(entityAccount.getAccountTransactions().size(), accountDto.getAccountTransactions().size());
        assertEquals(1, accountDto.getAccountTransactions().size());

        AccountTransactionDto accountTransactionDto= accountDto.getAccountTransactions().stream().findFirst().get();
        assertEquals(accountTransaction1.getId(), accountTransactionDto.getId());
        assertEquals(accountTransaction1.getReferenceNo(), accountTransactionDto.getReferenceNo());
        assertEquals(accountTransaction1.getNewBalance(), accountTransactionDto.getNewBalance());
        assertEquals(accountTransaction1.getAmount(), accountTransactionDto.getAmount());
    }
}
