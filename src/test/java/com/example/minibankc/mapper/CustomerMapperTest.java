package com.example.minibankc.mapper;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import org.junit.jupiter.api.Test;
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
class CustomerMapperTest {

    @Autowired
    private CustomerMapper mapper;

    @Test
    void givenEntityToDto_whenMaps_thenCorrect() {
        Customer entity= new Customer();
        entity.setName("Mahdi in test Mapper");
        entity.setSurname("Sharifi in test mapper");
        CustomerDto dto = mapper.toDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getSurname(), dto.getSurname());
    }
    @Test
    void givenDtoToEntity_whenMaps_thenCorrect() {
        CustomerDto dto = new CustomerDto();
        dto.setName("Mahdi in test Mapper");
        dto.setSurname("Sharifi in test mapper");
        Customer entity = mapper.toEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getSurname(), entity.getSurname());
    }
    @Test
    void givenEntityToDto_whenMaps_thenCorrect_allDependencies(){
        Customer entity;
        Account account1;
        Account account2;
        AccountTransaction accountTransaction1;
        AccountTransaction accountTransaction2;
        AccountTransaction accountTransaction3;
        Set<Account> accountList=new HashSet<>();
        Set<AccountTransaction> accountTransactionList=new HashSet<>();
        Set<AccountTransaction> accountTransactionList2=new HashSet<>();

        entity=new Customer("Mahdi Mock Customer","Sharifi Mock Customer",accountList);
        entity.setId(1L);
        account1 = new Account(1L,accountTransactionList,entity);
        account1.setId(1L);
        account1.setCreatedAt(new Date());

        account2 = new Account(2L,accountTransactionList2,entity);
        account2.setId(2L);

        accountList.add(account1);
        accountList.add(account2);

        accountTransaction1 = new AccountTransaction(1,10,1L,account1);
        accountTransaction1.setId(1L);
        accountTransaction2 = new AccountTransaction(2,5,2L,account1);
        accountTransaction2.setId(2L);

        accountTransaction3 = new AccountTransaction(-1,1,3L,account2);
        accountTransaction3.setId(3L);

        accountTransactionList.add(accountTransaction1);
        accountTransactionList.add(accountTransaction2);

        accountTransactionList2.add(accountTransaction3);

        CustomerDto dto = mapper.toDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getSurname(), dto.getSurname());
        assertEquals(entity.getAccounts().size(), dto.getAccounts().size());

    }
}
