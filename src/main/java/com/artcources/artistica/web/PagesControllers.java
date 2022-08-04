package com.artcources.artistica.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesControllers {


  @GetMapping("/about")
  public String about() {
    return "about";
  }



}
