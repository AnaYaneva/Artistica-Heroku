package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.UserProfileUpdateBindingModel;
import com.artcources.artistica.model.service.UserProfileUpdateServiceModel;
import com.artcources.artistica.model.view.UserProfileViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.service.UserService;
import com.artcources.artistica.service.WorkshopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final WorkshopService workshopService;

    public UserController(UserService userService, ModelMapper modelMapper, WorkshopService workshopService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.workshopService = workshopService;
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
        UserProfileViewModel userProfileViewModel
                = this.userService.getUserProfileViewModelByEmail(principal.getName());
        UserProfileUpdateBindingModel userProfileUpdateBindingModel
                = this.modelMapper.map(userProfileViewModel, UserProfileUpdateBindingModel.class);

        List<WorkshopsAllViewModel> workshops
                =this.workshopService.getWorkshopsAttendingByUserEmail(principal.getName());

        // add attributes
        model.addAttribute("workshops",workshops);
        model.addAttribute("userProfileViewModel", userProfileViewModel);
        model.addAttribute("userProfileUpdateBindingModel",userProfileUpdateBindingModel);

        List<WorkshopsAllViewModel> mentorWorkshops
                =this.workshopService.getCurrentMentorWorkshops(principal);

        return "user-details";
    }

    @GetMapping("/profile/errors")
    public String profileErrors (Model model,Principal principal) {
        UserProfileViewModel userProfileViewModel
                = this.userService.getUserProfileViewModelByEmail(principal.getName());
        model.addAttribute("user", userProfileViewModel);
        return "user-details";
    }

    @PatchMapping("/profile")
    public String profileUpdate(@Valid UserProfileUpdateBindingModel userProfileUpdateBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hasErrors",true);
            redirectAttributes.addFlashAttribute("userProfileUpdateBindingModel", userProfileUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateBindingModel", bindingResult);
            return "redirect:/users/profile/errors";
        }

        UserProfileUpdateServiceModel userProfileUpdateServiceModel
                = this.modelMapper.map(userProfileUpdateBindingModel, UserProfileUpdateServiceModel.class);
        this.userService.updateUserProfile(userProfileUpdateServiceModel,principal);

        return "redirect:/users/profile";
    }


}
