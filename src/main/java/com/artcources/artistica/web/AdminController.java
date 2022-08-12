package com.artcources.artistica.web;


import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.WorkshopsAllServiceModel;
import com.artcources.artistica.model.view.UsersAllViewModel;
import com.artcources.artistica.repository.UserRoleRepository;
import com.artcources.artistica.service.MentorService;
import com.artcources.artistica.service.UserRoleService;
import com.artcources.artistica.service.UserService;
import com.artcources.artistica.service.WorkshopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final MentorService mentorService;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final WorkshopService workshopService;

    public AdminController(UserService userService, MentorService mentorService, UserRoleService userRoleService, ModelMapper modelMapper, WorkshopService workshopService) {
        this.userService = userService;
        this.mentorService = mentorService;
        this.userRoleService = userRoleService;

        this.modelMapper = modelMapper;
        this.workshopService = workshopService;
    }

    //MANAGE USERS PAGE
    @GetMapping("/manage-users")
    public String adminPanel(Model model) {
        List<UsersAllViewModel> usersAllViewModels = this.userService.findAllUsers()
                .stream()
                .map(usersAllServiceModel -> this.modelMapper.map(usersAllServiceModel, UsersAllViewModel.class)
                        .setAdmin(usersAllServiceModel.getUserRoles().contains(this.userRoleService.findRoleByName(UserRoleEnum.ADMIN)))
                        .setMentor(usersAllServiceModel.getUserRoles().contains(this.userRoleService.findRoleByName(UserRoleEnum.MENTOR))))
                .collect(Collectors.toList());
//        List<MentorsAllViewModel> mentorsAllViewModels = this.mentorService.findAllMentors()
//                .stream()
//                .map(mentorsAllServiceModel -> this.modelMapper.map(mentorsAllServiceModel, MentorsAllViewModel.class))
//                .collect(Collectors.toList());
        model.addAttribute("allUsers",usersAllViewModels);
//        model.addAttribute("allMentors",mentorsAllViewModels);
//        model.addAttribute("admin", UserRoleEnum.ADMIN);
        return "admin-manage-users";
    }

    //ADD ADMIN ROLE
    @PatchMapping("/manage-users/add-admin")
    public String makeAdmin(@RequestParam String username){
        boolean isUserExist = this.userService.existByEmail(username);
        if(isUserExist) {
            this.userService.makeUserAdmin(username);
        }

        return "redirect:/admin/manage-users";
    }

    //REMOVE ADMIN ROLE
    @PatchMapping("/manage-users/remove-admin")
    public String removeAdmin(@RequestParam String username){
        boolean isUserExist = this.userService.existByEmail(username);
       if(isUserExist) {
            this.userService.removeAdminRole(username);
        }

        return "redirect:/admin/manage-users";
    }

    //REVIEW WORKSHOPS
    @GetMapping("/review-workshops")
    public String reviewWorkshops(Model model) {
        List<WorkshopsAllServiceModel> allPendingWorkshops = this.workshopService.getAllPendingWorkshopsServiceModel()
                .stream()
                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
        model.addAttribute("pendingWorkshops", allPendingWorkshops);
        return "admin-approve-workshop";
    }

    //APPROVE WORKSHOP
    @PatchMapping("/review-workshops/approve/{id}")
    public String approveWorkshop(@PathVariable Long id) {
        this.workshopService.approveWorkshop(id);
        return "redirect:/admin/review-workshops";
    }

    //REJECT WORKSHOP
    @PatchMapping("/review-workshops/reject/{id}")
    public String rejectWorkshop(@PathVariable Long id) {
        this.workshopService.rejectWorkshop(id);
        return "redirect:/admin/review-workshops";
    }

}
