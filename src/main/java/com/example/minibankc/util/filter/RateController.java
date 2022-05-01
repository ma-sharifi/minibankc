package com.example.minibankc.util.filter;

import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 5/1/22
 */
@Target({ElementType.METHOD})
@Retention(RUNTIME)
public @interface RateController {

    int rateLimit() default 2; // this's threshold.

    String parameterName() default "X-Request-Id";

    /**
     * if response status is equal httpStatusResponse, then the condition is applied;
     */
    HttpStatus httpStatusResponse() default HttpStatus.OK;
}
