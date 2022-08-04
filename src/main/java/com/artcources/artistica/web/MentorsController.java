package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.MentorProfileUpdateBindingModel;
import com.artcources.artistica.model.binding.MentorRegisterBindingModel;
import com.artcources.artistica.model.service.MentorRegisterServiceModel;
import com.artcources.artistica.model.view.MentorProfileViewModel;
import com.artcources.artistica.model.view.MentorsAllViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.service.MentorService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mentors")
public class MentorsController {

    private final ModelMapper modelMapper;
    private final MentorService mentorService;
    private final WorkshopService workshopService;

    public MentorsController(ModelMapper modelMapper, MentorService mentorService, WorkshopService workshopService) {
        this.modelMapper = modelMapper;
        this.mentorService = mentorService;
        this.workshopService = workshopService;
    }

    @GetMapping("")
    public String all(Model model) {
        List<MentorsAllViewModel> mentorsAllViewModels = this.mentorService.findAllMentors()
                .stream()
                .map(mentorsAllServiceModel -> this.modelMapper.map(mentorsAllServiceModel, MentorsAllViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("mentors", mentorsAllViewModels);

        return "mentors-all";
    }

    @ModelAttribute
    public MentorRegisterBindingModel mentorRegisterBindingModel() {
        return new MentorRegisterBindingModel();
    }

    @GetMapping("/register/mentor")
    public String registerMentor(Model model) {
//        model.addAttribute("cities", this.cityService.getAllCities());
//        model.addAttribute("regions", this.regionService.getAllRegions());
//        model.addAttribute("categories", CategoryMentorEnum.values());
        return "register-mentor";
    }

    @PostMapping("/register/mentor")
    public String registerMentor(@Valid @ModelAttribute("mentorRegisterBindingModel") MentorRegisterBindingModel mentorRegisterBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mentorRegisterBindingModel", mentorRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mentorRegisterBindingModel", bindingResult);
            return "redirect:/register/mentor";
        }

        MentorRegisterServiceModel mentorRegisterServiceModel = this.modelMapper.map(mentorRegisterBindingModel, MentorRegisterServiceModel.class);
        this.mentorService.save(mentorRegisterServiceModel);
        return "redirect:/";
    }

    //MY PROFILE PAGE
    @ModelAttribute
    public MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel() {
        return new MentorProfileUpdateBindingModel();
    }

    @GetMapping("/mentor-profile")
    public String profile (Model model, Principal principal) {
        if(!model.containsAttribute("hasErrors")) {
            model.addAttribute("hasErrors",false);
        }
        //add binding mentorProfileUpdateBindingModel on get request so we can fill form inputs with needed values
        MentorProfileViewModel mentorProfileViewModel
                = this.mentorService.getMentorProfileViewModelByEmail(principal.getName());
        MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel
                = this.modelMapper.map(mentorProfileViewModel, MentorProfileUpdateBindingModel.class);
       // mentorProfileUpdateBindingModel.setCityName(mentorProfileViewModel.getAddress().getCityName());
        // add attributes
        //model.addAttribute("cities", this.cityService.getAllCities());
        model.addAttribute("mentorProfileUpdateBindingModel",mentorProfileUpdateBindingModel);
        model.addAttribute("mentorProfileViewModel",mentorProfileViewModel);
        return "mentor-profile";
    }

    @GetMapping("/mentor-profile/errors")
    public String profileErrors (Model model,Principal principal) {
        MentorProfileViewModel mentorProfileViewModel
                = this.mentorService.getMentorProfileViewModelByEmail(principal.getName());
        //model.addAttribute("cities", this.cityService.getAllCities());
        model.addAttribute("mentorProfileViewModel",mentorProfileViewModel);
        return "mentor-profile";
    }


    @PatchMapping("/mentor-profile")
    public String profileUpdate(@Valid MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hasErrors",true);
            redirectAttributes.addFlashAttribute("mentorProfileUpdateBindingModel", mentorProfileUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mentorProfileUpdateBindingModel", bindingResult);
            return "redirect:/mentor-profile/errors";
        }

        this.mentorService.updateMentorProfile(mentorProfileUpdateBindingModel,principal);

        return "redirect:/mentor-profile";
    }

    //MY OFFERS
    @GetMapping("/mentor-profile/my-offers")
    public String myOffers (Model model, Principal principal) {
        List<WorkshopsAllViewModel> mentorOffers
                =this.workshopService.getCurrentUserWorkshops(principal);
        //model.addAttribute("currentUserCompanyName",this.mentorService.getCurrentUserCompanyName(principal));
        model.addAttribute("mentorOffers",mentorOffers);
        return "my-offers";
    }
}
