package com.example.minibankc.entity;

import com.example.minibankc.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/26/22
 */
class CustomerTest {
    Customer customer ;

    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void getName() {
        String name= "Mani";
        customer.setName(name);
        assertEquals(name , customer.getName());
    }

    @Test
    void getSurname() {
        String surName = "Sharifi";
        customer.setSurname(surName);
        assertEquals(surName, customer.getSurname());
    }

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);
        customer2.setId(2L);
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.setId(null);
        assertThat(customer1).isNotEqualTo(customer2);
    }
}