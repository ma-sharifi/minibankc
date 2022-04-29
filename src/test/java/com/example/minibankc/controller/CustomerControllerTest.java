package com.example.minibankc.controller;

import com.example.minibankc.dto.CustomerDto;
import com.example.minibankc.entity.Customer;
import com.example.minibankc.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    void getCustomerInfo(){
        String resourceUrl = uri+"/v1/customers/"+1;
        ResponseEntity<CustomerDto> responseEntity = this.restTemplate.exchange(resourceUrl, HttpMethod.GET, null, CustomerDto.class);
        CustomerDto customerDto=responseEntity.getBody();
        assertThat(customerDto).toString().contains("-1");

        assertEquals("Mahdi",customerDto.getName() );
        assertEquals("Sharifi",customerDto.getSurname() );
    }

}
