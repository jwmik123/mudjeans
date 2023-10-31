package com.team3.mudjeans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.PRECONDITION_FAILED)
public class PreConditionFailedException extends RuntimeException {
    public PreConditionFailedException() {

    }
    public PreConditionFailedException(String message) {
        super(message);
    }
    public PreConditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
