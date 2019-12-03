package com.vinicius.pixeon.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vinícius.carvalho
 *
 * RuntimeException to trigger rollback operations
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PixeonNotFoundException extends RuntimeException {

    public PixeonNotFoundException(String message) {
        super(message);
    }

    public PixeonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
