package com.example.minibankc.exception;

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
public class CustomerNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private static final String entityName="customer";

    private static final String errorKey="customernofound";

    public CustomerNotFoundException(Long id) {
        super(ErrorConstants.DEFAULT_TYPE, "Could not find customer with id: " + id, Status.NOT_FOUND, null, null, null, getAlertParameters(entityName, errorKey));
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
