package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.CoursesSearchBindingModel;
import com.artcources.artistica.model.binding.VideoAddBindingModel;
import com.artcources.artistica.model.binding.WorkshopAddBindingModel;
import com.artcources.artistica.model.binding.WorkshopUpdateBindingModel;
import com.artcources.artistica.model.entity.VideoEntity;
import com.artcources.artistica.model.enums.CourseCategoryEnum;
import com.artcources.artistica.model.service.VideoAddServiceModel;
import com.artcources.artistica.model.service.WorkshopAddServiceModel;
import com.artcources.artistica.model.service.WorkshopUpdateServiceModel;
import com.artcources.artistica.model.view.WorkshopDetailsViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.service.CloudinaryService;
import com.artcources.artistica.service.CloudinaryVideo;
import com.artcources.artistica.service.WorkshopService;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/courses")
public class CoursesController {

    private final CloudinaryService cloudinaryService;

    private final WorkshopService workshopService;

    private final ModelMapper modelMapper;

    public CoursesController(CloudinaryService cloudinaryService, WorkshopService workshopService, ModelMapper modelMapper) {
        this.cloudinaryService = cloudinaryService;
        this.workshopService = workshopService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/all")
    public String all(Model model) {
//        List<WorkshopsAllViewModel> approvedWorkshops = this.workshopService.getAllApprovedWorkshopsServiceModel()
//                .stream()
//                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
//                .collect(Collectors.toList());
//        model.addAttribute("approvedWorkshops", approvedWorkshops);
        //model.addAttribute("allCategories", CategoryMentorEnum.values());

            model.addAttribute("watercolor", workshopService.findAllWorkshopsByCategoryName(CourseCategoryEnum.WATERCOLOR));
            model.addAttribute("acrylic", workshopService.findAllWorkshopsByCategoryName(CourseCategoryEnum.ACRYLIC));
            model.addAttribute("pencils", workshopService.findAllWorkshopsByCategoryName(CourseCategoryEnum.GRAPHITE_PENCILS));
            model.addAttribute("pastels", workshopService.findAllWorkshopsByCategoryName(CourseCategoryEnum.SOFT_PASTELS));

        return "workshops-all";
    }

    @GetMapping("/add")
    public String add() {
        return "workshop-add";
    }

    @PostMapping("/add")
    public String add(@Valid WorkshopAddBindingModel workshopAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("workshopAddBindingModel", workshopAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workshopAddBindingModel", bindingResult);
            return "redirect:/courses/add";
        }

        WorkshopAddServiceModel workshopAddServiceModel = this.modelMapper.map(workshopAddBindingModel, WorkshopAddServiceModel.class);
        Long idWorkshop=this.workshopService.addNewWorkshop(workshopAddServiceModel, principal);
        return "redirect:/courses/" + idWorkshop;
    }

    private VideoEntity createVideoEntity(MultipartFile file, String title) throws IOException {
        final CloudinaryVideo uploaded = this.cloudinaryService.upload(file);
        return (VideoEntity) new VideoEntity()
                .setPublicId(uploaded.getPublicId())
                .setName(title)
                .setUrl(uploaded.getUrl());
    }

    @ModelAttribute
    public CoursesSearchBindingModel coursesSearchBindingModel(){
        return new CoursesSearchBindingModel();
    }

    @ModelAttribute("workshopDetailsViewModel")
    public WorkshopDetailsViewModel workshopDetailsViewModel() {
        return new WorkshopDetailsViewModel();
    }

    @ModelAttribute("workshopDetailsViewModel")
    public WorkshopAddBindingModel workshopAddBindingModel() {
        return new WorkshopAddBindingModel();
    }

    // EDIT OFFER
    @ModelAttribute
    public WorkshopUpdateBindingModel workshopUpdateBindingModel() {
        return new WorkshopUpdateBindingModel();
    }

    @GetMapping("/workshops/{id}/update")
    public String updateWorkshop(@PathVariable Long id, Model model) {
        WorkshopDetailsViewModel workshopDetailsViewModel = this.workshopService.findWorkshopViewModelById(id);
        WorkshopUpdateBindingModel workshopUpdateBindingModel = this.modelMapper.map(workshopDetailsViewModel, WorkshopUpdateBindingModel.class);
        model.addAttribute("workshop", workshopDetailsViewModel);
        model.addAttribute("workshopUpdateBindingModel", workshopDetailsViewModel);
        return "workshop-update";
    }

    @PatchMapping("/workshops/{id}/update")
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

        WorkshopUpdateServiceModel workshopUpdateServiceModel = this.modelMapper.map(workshopUpdateBindingModel, WorkshopUpdateServiceModel.class);
        this.workshopService.updateWorkshop(workshopUpdateServiceModel,id);

        return "redirect:/workshops/"+id;
    }

    @GetMapping("/workshops/{id}/update/errors")
    public String updateWorkshopErrors(@PathVariable Long id, Model model) {
        WorkshopDetailsViewModel workshopDetailsViewModel = this.workshopService.findWorkshopViewModelById(id);
        model.addAttribute("workshop", workshopDetailsViewModel);
        return "workshop-update";
    }

    //ADD NEW PICTURE TO THE EXISTING OFFER
    @ModelAttribute
    public VideoAddBindingModel videoAddBindingModel() {
        return new VideoAddBindingModel();
    }

    @PostMapping("/workshops/{id}/update/add-video")
    public String addNewVideo(@PathVariable Long id,
                              @Valid VideoAddBindingModel videoAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("videoAddHasErrors",true);
            redirectAttributes.addFlashAttribute("videoAddBindingModel", videoAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoAddBindingModel", bindingResult);
            return "redirect:/workshops/"+id+"/update";
        }

        VideoAddServiceModel videoAddServiceModel = this.modelMapper.map(videoAddBindingModel, VideoAddServiceModel.class);
        this.workshopService.addNewVideo(videoAddServiceModel,id);

        return "redirect:/workshops/"+id+"/update";
    }


    //DELETE PICTURE FROM EXISTING OFFER
    @DeleteMapping("/workshops/{id}/update/delete-video/{picId}")
    public String deleteVideo(@PathVariable Long id, @PathVariable Long picId) {
        this.workshopService.deleteVideo(picId);
        return "redirect:/workshops/"+id+"/update";
    }


    //DELETE OFFER
    @DeleteMapping("/workshops/{id}/delete")
    public String deleteWorkshop(@PathVariable Long id) {
        this.workshopService.deleteWorkshop(id);
        return "redirect:/mentor-profile/my-workshops";
    }

}
