package com.artcources.artistica.exceptionHandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArtisticaControllerAdvice {
   // @ExceptionHandler({Exception.class})
    public String handleError() {
        return "redirect:error";
    }
}
