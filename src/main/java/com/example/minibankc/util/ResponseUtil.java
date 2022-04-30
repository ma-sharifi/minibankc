package com.example.minibankc.util;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/22/22
 * Extracted from JHipster Code generator
 */

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ResponseUtil {
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map((response) -> {
            return ResponseEntity.ok().headers(header).body(response);
        }).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }
}
