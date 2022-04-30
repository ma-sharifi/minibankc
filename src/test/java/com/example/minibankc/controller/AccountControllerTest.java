package com.example.minibankc.controller;

import com.example.minibankc.EndToEndTest;
import com.example.minibankc.dto.AccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

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
@EndToEndTest
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
    void openAccountForExistingCustomer() {
        String resourceUrl = uri + "/v1/customers/" + 1 + "/accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Initial-Credit", "5");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<AccountDto> responseEntity = this.restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, AccountDto.class);
        AccountDto accountDto = responseEntity.getBody();
        assertEquals(5, accountDto.getBalance());
        assertEquals(1, accountDto.getAccountTransactions().size());
    }

    @Test
    void getAccountForExistingAccount() {
        String resourceUrl = uri + "/v1/accounts/" + 1;
        ResponseEntity<AccountDto> responseEntity = this.restTemplate.getForEntity(resourceUrl, AccountDto.class);
        AccountDto accountDto = responseEntity.getBody();
        assertEquals(1, accountDto.getId());
    }
}
