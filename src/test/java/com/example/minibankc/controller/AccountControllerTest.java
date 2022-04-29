package com.example.minibankc.controller;

import com.example.minibankc.dto.AccountDto;
import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Account;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    private String uri;

    @LocalServerPort
    private int port;

    @Autowired
    private AccountController accountController;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    void contextLoads() throws Exception {
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
