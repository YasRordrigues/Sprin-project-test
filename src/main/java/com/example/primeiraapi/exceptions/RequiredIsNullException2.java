package com.example.primeiraapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredIsNullException2 extends RuntimeException{
    public RequiredIsNullException2(){
        super("It is not allowed to persist a null object!");
    }

    public RequiredIsNullException2(String ex) {
        super(ex);
    }

    private static final long serialVersionUID = 1L;
}
