package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.CoursesSearchBindingModel;
import com.artcources.artistica.model.binding.UserRegisterBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/all")
    public String all() {
        return "course";
    }

    @ModelAttribute
    public CoursesSearchBindingModel coursesSearchBindingModel(){
        return new CoursesSearchBindingModel();
    }
}
