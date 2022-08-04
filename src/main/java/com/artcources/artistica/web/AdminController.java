package com.artcources.artistica.web;


import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.WorkshopsAllServiceModel;
import com.artcources.artistica.model.view.MentorsAllViewModel;
import com.artcources.artistica.model.view.UsersAllViewModel;
import com.artcources.artistica.service.MentorService;
import com.artcources.artistica.service.UserService;
import com.artcources.artistica.service.WorkshopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final UserService userService;
    private final MentorService mentorService;
    private final ModelMapper modelMapper;
    private final WorkshopService workshopService;

    public AdminController(UserService userService, MentorService mentorService, ModelMapper modelMapper, WorkshopService workshopService) {
        this.userService = userService;
        this.mentorService = mentorService;

        this.modelMapper = modelMapper;
        this.workshopService = workshopService;
    }

    //MANAGE USERS PAGE
    @GetMapping("/admin/manage-users")
    public String adminPanel(Model model) {
        List<UsersAllViewModel> usersAllViewModels = this.userService.findAllUsers()
                .stream()
                .map(usersAllServiceModel -> this.modelMapper.map(usersAllServiceModel, UsersAllViewModel.class))
                .collect(Collectors.toList());
        List<MentorsAllViewModel> mentorsAllViewModels = this.mentorService.findAllMentors()
                .stream()
                .map(mentorsAllServiceModel -> this.modelMapper.map(mentorsAllServiceModel, MentorsAllViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("allUsers",usersAllViewModels);
        model.addAttribute("allSuppliers",mentorsAllViewModels);
        model.addAttribute("admin", UserRoleEnum.ADMIN);
        return "admin-manage-users";
    }

    //ADD ADMIN ROLE
    @PatchMapping("/admin/manage-users/add-admin")
    public String makeAdmin(@RequestParam String email){
        boolean isUserExist = this.userService.existByEmail(email);
        boolean isSupplierExist = this.mentorService.existByEmail(email);
        if(isSupplierExist) {
            this.mentorService.makeMentorAdmin(email);
        } else if(isUserExist) {
            this.userService.makeUserAdmin(email);
        }

        return "redirect:/admin/manage-users";
    }

    //REMOVE ADMIN ROLE
    @PatchMapping("/admin/manage-users/remove-admin")
    public String removeAdmin(@RequestParam String email){
        boolean isUserExist = this.userService.existByEmail(email);
        boolean isSupplierExist = this.mentorService.existByEmail(email);
        if(isSupplierExist) {
            this.mentorService.removeAdminRole(email);
        } else if(isUserExist) {
            this.userService.removeAdminRole(email);
        }

        return "redirect:/admin/manage-users";
    }

    //REVIEW OFFERS
    @GetMapping("/admin/review-workshops")
    public String reviewOffers(Model model) {
        List<WorkshopsAllServiceModel> allPendingWorkshops = this.workshopService.getAllPendingWorkshopsServiceModel()
                .stream()
                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
        model.addAttribute("pendingWorkshop", allPendingWorkshops);
        return "admin-approve-workshops";
    }

    //APPROVE OFFER
    @PatchMapping("/admin/review-offers/approve/{id}")
    public String approveOffer(@PathVariable Long id) {
        this.workshopService.approveWorkshop(id);
        return "redirect:/admin/review-offers";
    }

    @PatchMapping("/admin/review-offers/reject/{id}")
    public String rejectOffer(@PathVariable Long id) {
        this.workshopService.rejectWorkshop(id);
        return "redirect:/admin/review-offers";
    }

}
