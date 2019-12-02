package com.vinicius.pixeon.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vinícius.carvalho
 *
 * RuntimeException to trigger rollback operations
 */
@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class PixeonUnauthorizedException extends RuntimeException {

    public PixeonUnauthorizedException(String message) {
        super(message);
    }

    public PixeonUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
