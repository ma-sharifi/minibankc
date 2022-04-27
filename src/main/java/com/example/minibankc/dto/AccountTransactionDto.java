package com.example.minibankc.dto;

import com.example.minibankc.entity.AccountTransaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
/**
 * A DTO for the {@link AccountTransaction} entity.
 */

@EqualsAndHashCode(callSuper = true)
@Schema(description = "save Transaction of account")
@Data
public class AccountTransactionDto extends BaseDto {

    @NotNull
    private Long amount;

    private Long newBalance;

    private Long referenceNo;

    private AccountDto account;

}
