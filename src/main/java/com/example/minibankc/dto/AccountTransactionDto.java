package com.example.minibankc.dto;

import com.example.minibankc.entity.AccountTransaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@Data @ToString(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTransactionDto extends BaseDto {

    @NotNull(message = "#amount can not be null")
    private Long amount;

    @JsonProperty("new_balance")
    private Long newBalance;

    @JsonProperty("reference_no")
    private Long referenceNo;

}
