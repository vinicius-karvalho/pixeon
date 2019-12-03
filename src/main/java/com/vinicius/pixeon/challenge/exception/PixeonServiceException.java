package com.vinicius.pixeon.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vinícius.carvalho
 *
 * RuntimeException to trigger rollback operations
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PixeonServiceException extends RuntimeException {

    public PixeonServiceException(String message) {
        super(message);
    }

    public PixeonServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
