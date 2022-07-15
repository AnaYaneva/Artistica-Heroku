package com.artcources.artistica.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    @GetMapping("")
    public String all() {
        return "course";
    }

    @GetMapping("/online")
    public String online() {
        return "course";
    }

    @GetMapping("/onsite")
    public String onsite() {
        return "course";
    }
}
