package com.pozwizd.prominadaadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class OperationException extends RuntimeException {
    public OperationException(String operation, String message) {
        super("Ошибка при " + operation + ": " + message);
    }
}
