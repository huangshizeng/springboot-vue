package com.huang.vuedemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JsonParseException extends RuntimeException {

    public JsonParseException() {
        super();
    }
}
