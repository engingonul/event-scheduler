package com.mycompany.eventscheduler.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiError {

    private HttpStatusCode status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatusCode status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatusCode status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }
}
