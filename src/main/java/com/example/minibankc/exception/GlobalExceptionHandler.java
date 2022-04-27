package com.example.minibankc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity customerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        log.debug("customerNotFoundException = " + customerNotFoundException);
        return new ResponseEntity(customerNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
