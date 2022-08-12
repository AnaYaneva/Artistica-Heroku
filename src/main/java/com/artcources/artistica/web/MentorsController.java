package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.MediaAddBindingModel;
import com.artcources.artistica.model.binding.MentorProfileUpdateBindingModel;
import com.artcources.artistica.model.binding.MentorRegisterBindingModel;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.service.MentorServiceModel;
import com.artcources.artistica.model.view.MentorProfileViewModel;
import com.artcources.artistica.model.view.MentorsAllViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mentors")
public class MentorsController {

    private final ModelMapper modelMapper;
    private final MentorService mentorService;
    private final UserService userService;
    private final WorkshopService workshopService;

    private LocaleResolver localeResolver;


    public MentorsController(ModelMapper modelMapper, MentorService mentorService, UserService userService, WorkshopService workshopService, LocaleResolver localeResolver) {
        this.modelMapper = modelMapper;
        this.mentorService = mentorService;
        this.userService = userService;
        this.workshopService = workshopService;
        this.localeResolver = localeResolver;
    }

    @GetMapping("")
    public String all(Model model) {
        List<MentorsAllViewModel> mentorsAllViewModels = this.mentorService.findAllMentors()
                .stream()
                .map(mentorsAllServiceModel -> this.modelMapper.map(mentorsAllServiceModel, MentorsAllViewModel.class)
                        .setPhotoUrl(Optional.ofNullable(mentorsAllServiceModel.getPhotoUrl()).orElse("/images/default.jpg")))
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
                                 BindingResult bindingResult,
                                 @RequestParam MultipartFile photo,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mentorRegisterBindingModel", mentorRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mentorRegisterBindingModel", bindingResult);
            return "redirect:/mentors/register";
        }

        MentorServiceModel mentorRegisterServiceModel = this.modelMapper.map(mentorRegisterBindingModel, MentorServiceModel.class);
        mentorRegisterServiceModel.setPhoto(photo);
        this.mentorService.registerAndLogin(mentorRegisterServiceModel, localeResolver.resolveLocale(request));
        return "redirect:/";
    }

    //MY PROFILE PAGE
    @ModelAttribute
    public MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel() {
        return new MentorProfileUpdateBindingModel();
    }

    @GetMapping("/profile/myProfile")
    public String profile (Model model, Principal principal) {
        if(!model.containsAttribute("hasErrors")) {
            model.addAttribute("hasErrors",false);
        }
        MentorProfileViewModel mentorProfileViewModel
                = this.mentorService.getMentorProfileViewModelByEmail(principal.getName());
        MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel
                = this.modelMapper.map(mentorProfileViewModel, MentorProfileUpdateBindingModel.class);

        model.addAttribute("mentorProfileUpdateBindingModel",mentorProfileUpdateBindingModel);
        model.addAttribute("mentorProfileViewModel",mentorProfileViewModel);
        List<WorkshopsAllViewModel> mentorWorkshops
                =this.workshopService.getCurrentMentorWorkshops(principal);
        model.addAttribute("mentorWorkshops",mentorWorkshops);
        model.addAttribute("isOwner", true);

        return "mentor-details";
    }

    @GetMapping("/{username}")
    public String profile ( @PathVariable String username,Model model, Principal principal) {
        if(!model.containsAttribute("hasErrors")) {
            model.addAttribute("hasErrors",false);
        }
        MentorProfileViewModel mentorProfileViewModel
                = this.mentorService.getMentorProfileViewModelByEmail(username);
        MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel
                = this.modelMapper.map(mentorProfileViewModel, MentorProfileUpdateBindingModel.class);
        model.addAttribute("mentorProfileUpdateBindingModel",mentorProfileUpdateBindingModel);
        model.addAttribute("mentorProfileViewModel",mentorProfileViewModel);
        List<WorkshopsAllViewModel> mentorWorkshops
                =this.workshopService.getWorkshopsByMentorEmail(username);
        model.addAttribute("mentorWorkshops",mentorWorkshops);
        model.addAttribute("isOwner", username.equals(principal.getName()));

        return "mentor-details";
    }

    @GetMapping("/profile/myProfile/errors")
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

        return "redirect:/mentors/profile/myProfile";
    }

    @GetMapping("/editPhoto")
    public String editPhoto(Model model, Principal principal) {
        UserEntity mentor = mentorService.findMentorByEmail(principal.getName());
        MediaAddBindingModel mediaBindingModel = modelMapper.map(mentor.getPhoto(), MediaAddBindingModel.class);
        model.addAttribute("mediaBindingModel", mediaBindingModel);
        return "edit-photo";
    }

    @PatchMapping("/editPhoto")
    public String editPhoto(MediaAddBindingModel mediaAddBindingModel, Model model, Principal principal) {
        mentorService.updateMentorPhoto(mediaAddBindingModel, principal);
        return "redirect:/mentors/profile/myProfile";
    }

}
