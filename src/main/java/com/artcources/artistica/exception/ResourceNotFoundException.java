package com.artcources.artistica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason = "Resource not found!")
public class ResourceNotFoundException extends RuntimeException{
}
