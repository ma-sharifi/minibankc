package com.example.minibankc.exception;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@Getter
public class AccountNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private static final String ENTITY_NAME ="account";

    private static final String ERROR_KEY ="account.notfound.error.message";

    public AccountNotFoundException(Long id) {
        super(ErrorConstants.DEFAULT_TYPE, "Could not find account with id: " + id, Status.NOT_FOUND, null, null, null, getAlertParameters(ENTITY_NAME, ERROR_KEY));
    }
    public AccountNotFoundException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.NOT_FOUND, null, null, null, getAlertParameters(ENTITY_NAME, ERROR_KEY));
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
