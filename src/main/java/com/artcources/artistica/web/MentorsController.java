package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.MentorProfileUpdateBindingModel;
import com.artcources.artistica.model.binding.MentorRegisterBindingModel;
import com.artcources.artistica.model.service.MentorServiceModel;
import com.artcources.artistica.model.view.MentorProfileViewModel;
import com.artcources.artistica.model.view.MentorsAllViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.repository.UserRoleRepository;
import com.artcources.artistica.service.MentorService;
import com.artcources.artistica.service.UserService;
import com.artcources.artistica.service.WorkshopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mentors")
public class MentorsController {

    private final ModelMapper modelMapper;
    private final MentorService mentorService;
    private final UserService userService;
    private final WorkshopService workshopService;
    private final UserRoleRepository userRoleRepository;
    private LocaleResolver localeResolver;


    public MentorsController(ModelMapper modelMapper, MentorService mentorService, UserService userService, WorkshopService workshopService, UserRoleRepository userRoleRepository, LocaleResolver localeResolver) {
        this.modelMapper = modelMapper;
        this.mentorService = mentorService;
        this.userService = userService;
        this.workshopService = workshopService;
        this.userRoleRepository = userRoleRepository;
        this.localeResolver = localeResolver;
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

    @GetMapping("/register")
    public String registerMentor(Model model) {

        return "mentor-register";
    }

    @PostMapping("/register")
    public String registerMentor(@Valid @ModelAttribute("mentorRegisterBindingModel") MentorRegisterBindingModel mentorRegisterBindingModel,
                                 @RequestParam MultipartFile photo,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mentorRegisterBindingModel", mentorRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mentorRegisterBindingModel", bindingResult);
            return "redirect:/mentors/register";
        }

        MentorServiceModel mentorRegisterServiceModel = this.modelMapper.map(mentorRegisterBindingModel, MentorServiceModel.class);
        mentorRegisterServiceModel.setPhoto(photo);
//        UserRoleEntity mentorRole = userRoleRepository.findRoleByName(UserRoleEnum.MENTOR);
//        mentorRegisterServiceModel.setUserRoles(List.of(mentorRole));
        this.mentorService.registerAndLogin(mentorRegisterServiceModel, localeResolver.resolveLocale(request));
        return "redirect:/";
    }

    //MY PROFILE PAGE
    @ModelAttribute
    public MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel() {
        return new MentorProfileUpdateBindingModel();
    }

    @GetMapping("/profile")
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
        List<WorkshopsAllViewModel> mentorWorkshops
                =this.workshopService.getCurrentUserWorkshops(principal);
        model.addAttribute("mentorWorkshops",mentorWorkshops);

        return "mentor-details";
    }

    @GetMapping("/{username}")
    public String profile ( @PathVariable String username,Model model) {
        if(!model.containsAttribute("hasErrors")) {
            model.addAttribute("hasErrors",false);
        }
        //add binding mentorProfileUpdateBindingModel on get request so we can fill form inputs with needed values
        MentorProfileViewModel mentorProfileViewModel
                = this.mentorService.getMentorProfileViewModelByEmail(username);
        MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel
                = this.modelMapper.map(mentorProfileViewModel, MentorProfileUpdateBindingModel.class);
        // mentorProfileUpdateBindingModel.setCityName(mentorProfileViewModel.getAddress().getCityName());
        // add attributes
        //model.addAttribute("cities", this.cityService.getAllCities());
        model.addAttribute("mentorProfileUpdateBindingModel",mentorProfileUpdateBindingModel);
        model.addAttribute("mentorProfileViewModel",mentorProfileViewModel);
        List<WorkshopsAllViewModel> mentorWorkshops
                =this.workshopService.getCurrentUserWorkshopsByEmail(username);
        model.addAttribute("mentorWorkshops",mentorWorkshops);

        return "mentor-details";
    }

    @GetMapping("/profile/errors")
    public String profileErrors (Model model,Principal principal) {
        MentorProfileViewModel mentorProfileViewModel
                = this.mentorService.getMentorProfileViewModelByEmail(principal.getName());
        model.addAttribute("mentorProfileViewModel",mentorProfileViewModel);
        return "mentor-details";
    }


    @PatchMapping("/profile")
    public String profileUpdate(@Valid MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hasErrors",true);
            redirectAttributes.addFlashAttribute("mentorProfileUpdateBindingModel", mentorProfileUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mentorProfileUpdateBindingModel", bindingResult);
            return "redirect:/profile/errors";
        }

        this.mentorService.updateMentorProfile(mentorProfileUpdateBindingModel,principal);

        return "redirect:/mentors/profile";
    }


}
