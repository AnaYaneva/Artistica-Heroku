package com.artcources.artistica.exceptionHandling;

import com.artcources.artistica.exception.ResourceNotFoundException;
import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.exception.WorkshopNotFoundException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.firewall.RequestRejectedException;
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

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView modelAndView(NumberFormatException e){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("resource-not-found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView modelAndView(NullPointerException e){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("resource-not-found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

//    @ExceptionHandler(RequestRejectedException.class)
//    public ModelAndView modelAndView(RequestRejectedException e) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("access-denied");
//        modelAndView.setStatus(HttpStatus.NOT_FOUND);
//        return modelAndView;
//    }
}
