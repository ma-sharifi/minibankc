package com.example.minibankc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * This application Handle a mini bank operation
 * 1- oen an account for existing customer
 * 2- return customer information
 */
@SpringBootApplication
public class MinibankcApplication {

    public static void main(String[] args) {

        SpringApplication.run(MinibankcApplication.class, args);
    }

}
