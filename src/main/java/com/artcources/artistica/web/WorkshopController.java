package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.*;
import com.artcources.artistica.model.entity.MediaEntity;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.service.MediaAddServiceModel;
import com.artcources.artistica.model.service.WorkshopAddServiceModel;
import com.artcources.artistica.model.service.WorkshopUpdateServiceModel;
import com.artcources.artistica.model.view.WorkshopDetailsViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.repository.MediaRepository;
import com.artcources.artistica.service.CloudinaryService;
import com.artcources.artistica.service.CloudinaryMedia;
import com.artcources.artistica.service.WorkshopService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/workshops")
public class WorkshopController {

    private final CloudinaryService cloudinaryService;

    private final WorkshopService workshopService;

    private final ModelMapper modelMapper;

    private final MediaRepository mediaRepository;

    public WorkshopController(CloudinaryService cloudinaryService, WorkshopService workshopService, ModelMapper modelMapper, MediaRepository mediaRepository) {
        this.cloudinaryService = cloudinaryService;
        this.workshopService = workshopService;
        this.modelMapper = modelMapper;
        this.mediaRepository = mediaRepository;
    }

    @GetMapping("/search")
    public String search(Model model){
       return "search";
    }

    @PostMapping("/search")
    public String search(@Valid WorkshopSearchBindingModel workshopSearchBindingModel,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("workshopSearchBindingModel", workshopSearchBindingModel);
            model.addAttribute(
                    "org.springframework.validation.BindingResult.workshopSearchBindingModel",
                    bindingResult);
            return "search";
        }



        return "redirect:/workshops/search/" + workshopSearchBindingModel.getKeyword();
    }

    @GetMapping("/search/{keyword}")
    public String search(@PathVariable String keyword,Model model) {

            model.addAttribute("workshops", workshopService.searchWorkshop(keyword));



        model.addAttribute("title", "Search resylts by: " + keyword);
        return "workshops-all-by-criteria";
    }

    @GetMapping("/all")
    public String all(Model model) {
//        List<WorkshopsAllViewModel> watercolor = this.workshopService.getAllApprovedWorkshopsByCategory(WorkshopCategoryEnum.WATERCOLOR)
//                .stream()
//                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
//                .collect(Collectors.toList());
//        List<WorkshopsAllViewModel> acrylic = this.workshopService.getAllApprovedWorkshopsByCategory(WorkshopCategoryEnum.ACRYLIC)
//                .stream()
//                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
//                .collect(Collectors.toList());
//        List<WorkshopsAllViewModel> graphitePencils = this.workshopService.getAllApprovedWorkshopsByCategory(WorkshopCategoryEnum.GRAPHITE_PENCILS)
//                .stream()
//                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
//                .collect(Collectors.toList());
//        List<WorkshopsAllViewModel> softPastels = this.workshopService.getAllApprovedWorkshopsByCategory(WorkshopCategoryEnum.SOFT_PASTELS)
//                .stream()
//                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
//                .collect(Collectors.toList());
//        model.addAttribute("watercolor", watercolor);
//        model.addAttribute("watercolorCount", watercolor.size());
//        model.addAttribute("acrylic", acrylic);
//        model.addAttribute("watercolorCount", watercolor);
//        model.addAttribute("graphite_pencils", graphitePencils);
//        model.addAttribute("watercolorCount", watercolor);
//        model.addAttribute("soft_pastels", softPastels);
//        model.addAttribute("watercolorCount", watercolor.size());

        return "workshops-all";
    }

    //SHOW ALL WORKSHOPS BY CATEGORY
    @GetMapping("/categories/{category}")
    public String allWorkshopsByCategory(@PathVariable String category,Model model) {
        List<WorkshopsAllViewModel> allApprovedWorkshopsByCategory = this.workshopService.getAllApprovedWorkshopsByCategory(WorkshopCategoryEnum.valueOf(category.toUpperCase()))
                .stream()
                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("workshops",allApprovedWorkshopsByCategory);
        model.addAttribute("title", "By Category: " + category);
        return "workshops-all-by-criteria";
    }

    //SHOW ALL WORKSHOPS BY EXP LEVEL
    @GetMapping("/experienceLevel/{experienceLevel}")
    public String allWorkshopsByExperienceLevel(@PathVariable String experienceLevel,Model model) {
        List<WorkshopsAllViewModel> allApprovedWorkshopsByExpLevel = this.workshopService.getAllApprovedWorkshopsByExperienceLevel(ExperienceLevelEnum.valueOf(experienceLevel))
                .stream()
                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("workshops",allApprovedWorkshopsByExpLevel);
        model.addAttribute("title", "By Experience Level: " + experienceLevel);
        return "workshops-all-by-criteria";
    }

    //WORKSHOP DETAILS PAGE
    @ModelAttribute("workshopDetailsViewModel")
    public WorkshopDetailsViewModel workshopDetailsViewModel() {
        return new WorkshopDetailsViewModel();
    }

    @GetMapping("/{id}")
    public String workshopDetails(@PathVariable Long id, Model model,Principal principal) {
        OnlineWorkshopEntity workshop = workshopService.getWorkshopById(id);

        boolean isCurrentUserStudent = this.workshopService.isCurrentUserStudent(principal, id);
        boolean isCurrentUserOwner = this.workshopService.isCurrentUserOwner(principal, id);
        WorkshopDetailsViewModel workshopDetailsViewModel = this.workshopService.findWorkshopViewModelById(id);
        model.addAttribute("isCurrentUserOwner",isCurrentUserOwner);
        model.addAttribute("workshop", workshopDetailsViewModel);
        model.addAttribute("mentor", workshopDetailsViewModel.getMentor());
        model.addAttribute("isCurrentUserStudent", isCurrentUserStudent);

        return "workshop-details";
    }

    // ADD WORKSHOP
    @ModelAttribute
    public WorkshopAddBindingModel workshopAddBindingModel() {
        return new WorkshopAddBindingModel();
    }

    @GetMapping("/add")
    public String add() {
        return "workshop-add";
    }

    @PostMapping("/add")
    public String add(WorkshopAddBindingModel workshopAddBindingModel,
                      @RequestPart("referencePhoto") MultipartFile referencePhoto,
                      @RequestPart("finalPhoto") MultipartFile finalPhoto,
                      @RequestPart("video") MultipartFile video,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("workshopAddBindingModel", workshopAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workshopAddBindingModel", bindingResult);
            return "redirect:/workshops/add";
        }

        WorkshopAddServiceModel workshopAddServiceModel = this.modelMapper.map(workshopAddBindingModel, WorkshopAddServiceModel.class);
        workshopAddServiceModel.setFinalPhoto(finalPhoto);
        workshopAddServiceModel.setReferencePhoto(referencePhoto);
        workshopAddServiceModel.setVideo(video);
        Long idWorkshop=this.workshopService.addNewWorkshop(workshopAddServiceModel, principal);
        return "redirect:/workshops/" + idWorkshop;
    }


//    private MediaEntity createMediaEntity(MultipartFile file, String title) throws IOException {
//        final CloudinaryMedia uploaded = this.cloudinaryService.upload(file);
//        return new MediaEntity()
//                .setPublicId(uploaded.getPublicId())
//                .setName(title)
//                .setUrl(uploaded.getUrl());
//    }


    @ModelAttribute
    public WorkshopSearchBindingModel workshopSearchBindingModel(){
        return new WorkshopSearchBindingModel();
    }


    // EDIT WORKSHOP
    @ModelAttribute
    public WorkshopUpdateBindingModel workshopUpdateBindingModel() {
        return new WorkshopUpdateBindingModel();
    }

    @GetMapping("/{id}/update")
    public String updateWorkshop(@PathVariable Long id, Model model) {
        WorkshopDetailsViewModel workshopDetailsViewModel = this.workshopService.findWorkshopViewModelById(id);
        WorkshopUpdateBindingModel workshopUpdateBindingModel = this.modelMapper.map(workshopDetailsViewModel, WorkshopUpdateBindingModel.class);
        model.addAttribute("workshop", workshopDetailsViewModel);
        model.addAttribute("workshopUpdateBindingModel", workshopDetailsViewModel);
        return "workshop-update";
    }

    @PatchMapping("/{id}/update")
    public String updateWorkshop(@PathVariable Long id,
                                 @Valid WorkshopUpdateBindingModel workshopUpdateBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateHasErrors",true);
            redirectAttributes.addFlashAttribute("workshopUpdateBindingModel", workshopUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workshopUpdateBindingModel", bindingResult);
            return "redirect:/workshops/{id}/update/errors";
        }
        this.workshopService.updateWorkshop(workshopUpdateBindingModel,id);


        return "redirect:/workshops/"+id;
    }

    @GetMapping("/{id}/update/errors")
    public String updateWorkshopErrors(@PathVariable Long id, Model model) {
        WorkshopDetailsViewModel workshopDetailsViewModel = this.workshopService.findWorkshopViewModelById(id);
        model.addAttribute("workshop", workshopDetailsViewModel);
        return "workshop-update";
    }

    //ADD NEW PICTURE TO THE EXISTING WORKSHOP
    @ModelAttribute
    public MediaAddBindingModel videoAddBindingModel() {
        return new MediaAddBindingModel();
    }

   /* @PostMapping("/{id}/update/add-video")
    public String addNewVideo(@PathVariable Long id,
                              @Valid MediaAddBindingModel mediaAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("videoAddHasErrors",true);
            redirectAttributes.addFlashAttribute("videoAddBindingModel", mediaAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoAddBindingModel", bindingResult);
            return "redirect:/workshops/"+id+"/update";
        }

        MediaAddServiceModel mediaAddServiceModel = this.modelMapper.map(mediaAddBindingModel, MediaAddServiceModel.class);
        this.workshopService.addNewVideo(mediaAddServiceModel,id);

        return "redirect:/workshops/"+id+"/update";
    }*/


   /* //DELETE PICTURE FROM EXISTING WORKSHOP
    @DeleteMapping("/{id}/update/delete-video/{picId}")
    public String deleteVideo(@PathVariable Long id, @PathVariable Long picId) {
        this.workshopService.deleteVideo(picId);
        return "redirect:/workshops/"+id+"/update";
    }*/


    //DELETE WORKSHOP
    @DeleteMapping("/{id}/delete")
    public String deleteWorkshop(@PathVariable Long id) {
        this.workshopService.deleteWorkshop(id);
        return "redirect:/workshops/all";
    }

    //ADD ADMIN ROLE
    @PatchMapping("/addToList")
    public String addWorkshoptoList(@RequestParam Long id, Principal principal){
        boolean isWorkshopExist = this.workshopService.existById((id));
        if(isWorkshopExist) {
            this.workshopService.addWorkshopToUser((id),principal);
        }

        return "redirect:/workshops/"+id;
    }

    //REMOVE ADMIN ROLE
    @PatchMapping("/removeFromList")
    public String removeWorkshopFromList(@RequestParam Long id, Principal principal){
        boolean isUserExist = this.workshopService.existById(id);
        if(isUserExist) {
            this.workshopService.removeWorkshopFromUser(id,principal);
        }

        return "redirect:/workshops/"+id;
    }

}
