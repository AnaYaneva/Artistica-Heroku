package com.artcources.artistica.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mentors")
public class MentorsController {

    @GetMapping("")
    public String all() {
        return "mentors-all";
    }
}
