package com.example.minibankc.dto;

import com.example.minibankc.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
/**
 * A DTO for the {@link Customer} entity.
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "save customers data")
@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDto extends BaseDto {

    @Size(min = 2, max = 60)
    private String name;

    @Size(min = 2, max = 60)
    private String surname;

    private Set<AccountDto> accounts ;

    public CustomerDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
