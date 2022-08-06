package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.UserProfileUpdateBindingModel;
import com.artcources.artistica.model.service.UserProfileUpdateServiceModel;
import com.artcources.artistica.model.view.UserProfileUpdateViewModel;
import com.artcources.artistica.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    //MY PROFILE PAGE
    @ModelAttribute
    public UserProfileUpdateBindingModel userProfileUpdateBindingModel() {
        return new UserProfileUpdateBindingModel();
    }

    @GetMapping("/profile")
    public String profile (Model model, Principal principal) {
        if(!model.containsAttribute("hasErrors")) {
            model.addAttribute("hasErrors",false);
        }
        //add binding userProfileUpdateBindingModel on get request so we can fill form inputs with needed values
        UserProfileUpdateViewModel userProfileUpdateViewModel
                = this.userService.getUserProfileViewModelByEmail(principal.getName());
        UserProfileUpdateBindingModel userProfileUpdateBindingModel
                = this.modelMapper.map(userProfileUpdateViewModel, UserProfileUpdateBindingModel.class);
        // add attributes
        model.addAttribute("user",userProfileUpdateViewModel);
        model.addAttribute("userProfileUpdateBindingModel",userProfileUpdateBindingModel);
        return "user-profile";
    }

    @GetMapping("/user-profile/errors")
    public String profileErrors (Model model,Principal principal) {
        UserProfileUpdateViewModel userProfileUpdateViewModel
                = this.userService.getUserProfileViewModelByEmail(principal.getName());
        model.addAttribute("user",userProfileUpdateViewModel);
        return "user-profile";
    }

    @PatchMapping("/user-profile")
    public String profileUpdate(@Valid UserProfileUpdateBindingModel userProfileUpdateBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hasErrors",true);
            redirectAttributes.addFlashAttribute("userProfileUpdateBindingModel", userProfileUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateBindingModel", bindingResult);
            return "redirect:/user-profile/errors";
        }

        UserProfileUpdateServiceModel userProfileUpdateServiceModel
                = this.modelMapper.map(userProfileUpdateBindingModel, UserProfileUpdateServiceModel.class);
        this.userService.updateUserProfile(userProfileUpdateServiceModel,principal);

        return "redirect:/user-profile";
    }


}
