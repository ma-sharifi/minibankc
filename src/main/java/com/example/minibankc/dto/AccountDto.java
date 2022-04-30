package com.example.minibankc.dto;

import com.example.minibankc.entity.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

/**
 * A DTO for the {@link Account} entity.
 */
@Schema(description = "save customers account data")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto extends BaseDto {

    @NotNull(message = "#Balance can not be null")
    private Long balance;

    @JsonProperty("transactions")
    private Set<AccountTransactionDto> accountTransactions;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("AccountDto{" +
                "id=" + getId() +
                " ;balance=" + getBalance());
        if (accountTransactions != null)
            result.append(" ;transaction.size=").append(accountTransactions.size());
        result.append('}');

        return result.toString();
    }
}
