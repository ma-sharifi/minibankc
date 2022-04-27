package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {
    private String uri;

    @MockBean
    private AccountController accountController;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + 8080;
    }


    @Test
    void contextLoads() {
        assertThat(accountController).isNotNull();
    }


    @Test
    void openAccountForExistingCustomer(){
        String resourceUrl = uri+"/v1/customers/"+1+"/accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Initial-Credit","5");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<AccountDto> responseEntity = this.restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, AccountDto.class);
        AccountDto accountDto=responseEntity.getBody();
        assertEquals(5,accountDto.getBalance() );
        assertEquals(1,accountDto.getAccountTransactions().size() );
    }
}
