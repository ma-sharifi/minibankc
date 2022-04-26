package com.example.minibankc.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}