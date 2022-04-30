package com.example.minibankc.controller;

import com.example.minibankc.EndToEndTest;
import com.example.minibankc.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

@EndToEndTest
class CustomerControllerTest {
    private String uri;

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }


    @Test
    void contextLoads() {
        assertThat(customerController).isNotNull();
    }

    @Test
    void pingShouldReturnPong() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri + "/v1/ping", String.class);
        assertThat(responseEntity.getBody()).isEqualTo("pong");
    }

    @Test
    void getCustomerInfo() {
        String resourceUrl = uri + "/v1/customers/" + 1;
        ResponseEntity<CustomerDto> responseEntity = this.restTemplate.exchange(resourceUrl, HttpMethod.GET, null, CustomerDto.class);
        CustomerDto customerDto = responseEntity.getBody();
        assertThat(customerDto).toString().contains("-1");
        assertEquals("Mahdi", customerDto.getName());
        assertEquals("Sharifi", customerDto.getSurname());
    }

}
