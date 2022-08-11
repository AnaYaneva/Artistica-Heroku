package com.artcources.artistica.exceptionHandling;

import com.artcources.artistica.exception.ResourceNotFoundException;
import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.exception.WorkshopNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ArtisticaControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView modelAndView(ResourceNotFoundException e){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("resource-not-found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView modelAndView(UserNotFoundException e){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("user-not-found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(WorkshopNotFoundException.class)
    public ModelAndView modelAndView(WorkshopNotFoundException e){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("workshop-not-found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
