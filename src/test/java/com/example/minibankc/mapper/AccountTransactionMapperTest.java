package com.example.minibankc.mapper;

import com.example.minibankc.dto.AccountTransactionDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.AccountTransaction;
import com.example.minibankc.entity.Customer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@SpringBootTest
class AccountTransactionMapperTest {

    @Autowired
    private AccountTransactionMapper mapper ;

    @Test
    void givenEntityToDto_whenMaps_thenCorrect() {
        AccountTransaction entity= new AccountTransaction();
        entity.setAmount(1L);
        entity.setNewBalance(2L);
        entity.setId(1L);
        entity.setReferenceNo(123L);
        AccountTransactionDto dto = mapper.toDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAmount(), dto.getAmount());
        assertEquals(entity.getNewBalance(), dto.getNewBalance());
        assertEquals(entity.getReferenceNo(), dto.getReferenceNo());
    }
    @Test
    void givenDtoToEntity_whenMaps_thenCorrect() {
        AccountTransactionDto dto = new AccountTransactionDto();
        dto.setAmount(1L);
        dto.setNewBalance(2L);
        dto.setId(1L);
        dto.setReferenceNo(123L);
        AccountTransaction entity = mapper.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getAmount(), entity.getAmount());
        assertEquals(dto.getNewBalance(), entity.getNewBalance());
        assertEquals(dto.getReferenceNo(), entity.getReferenceNo());
    }
}
