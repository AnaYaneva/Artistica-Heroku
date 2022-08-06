package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.*;
import com.artcources.artistica.model.entity.MediaEntity;
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

        model.addAttribute("watercolor", workshopService.findAllWorkshopsByCategoryName(WorkshopCategoryEnum.WATERCOLOR));
        model.addAttribute("acrylic", workshopService.findAllWorkshopsByCategoryName(WorkshopCategoryEnum.ACRYLIC));
        model.addAttribute("pencils", workshopService.findAllWorkshopsByCategoryName(WorkshopCategoryEnum.GRAPHITE_PENCILS));
        model.addAttribute("pastels", workshopService.findAllWorkshopsByCategoryName(WorkshopCategoryEnum.SOFT_PASTELS));

        return "workshops-all";
    }

    //SHOW ALL WORKSHOPS BY CATEGORY
    @GetMapping("/category-{category}")
    public String allMentorsWorkshopsByCategory(@PathVariable String category,Model model) {
        List<WorkshopsAllViewModel> allApprovedWorkshopsByCategory = this.workshopService.getAllApprovedWorkshopsByCategory(category)
                .stream()
                .map(workshopsAllServiceModel -> this.modelMapper.map(workshopsAllServiceModel, WorkshopsAllViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("approvedWorkshops",allApprovedWorkshopsByCategory);
        model.addAttribute("allCategories", WorkshopCategoryEnum.values());
        return "all-workshops";
    }

    //WORKSHOP DETAILS PAGE
    @ModelAttribute("workshopDetailsViewModel")
    public WorkshopDetailsViewModel workshopDetailsViewModel() {
        return new WorkshopDetailsViewModel();
    }

    @GetMapping("/{id}")
    public String workshopDetails(@PathVariable Long id, Model model,Principal principal) {
        boolean isCurrentUserOwner = this.workshopService.isCurrentUserOwner(principal, id);
        WorkshopDetailsViewModel workshopDetailsViewModel = this.workshopService.findWorkshopViewModelById(id);
        model.addAttribute("isCurrentUserOwner",isCurrentUserOwner);
        model.addAttribute("workshop", workshopDetailsViewModel);
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
    public String add(@Valid WorkshopAddBindingModel workshopAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("workshopAddBindingModel", workshopAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workshopAddBindingModel", bindingResult);
            return "redirect:/workshops/add";
        }

        WorkshopAddServiceModel workshopAddServiceModel = this.modelMapper.map(workshopAddBindingModel, WorkshopAddServiceModel.class);
        Long idWorkshop=this.workshopService.addNewWorkshop(workshopAddServiceModel, principal);
        return "redirect:/workshops/" + idWorkshop;
    }

    //@PostMapping("/add")
    public String add(MediaBindingModel mediaBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("videoBindingModel", mediaBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoBindingModel", bindingResult);
            return "redirect:/courses/add";
        }
        MediaEntity entity = createMediaEntity(mediaBindingModel.getFile(), mediaBindingModel.getTitle());
        mediaRepository.save(entity);
        return "redirect:/workshops/all";
    }

    private MediaEntity createMediaEntity(MultipartFile file, String title) throws IOException {
        final CloudinaryMedia uploaded = this.cloudinaryService.upload(file);
        return new MediaEntity()
                .setPublicId(uploaded.getPublicId())
                .setName(title)
                .setUrl(uploaded.getUrl());
    }


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

        WorkshopUpdateServiceModel workshopUpdateServiceModel = this.modelMapper.map(workshopUpdateBindingModel, WorkshopUpdateServiceModel.class);
        this.workshopService.updateWorkshop(workshopUpdateServiceModel,id);

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

    @PostMapping("/{id}/update/add-video")
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
    }


    //DELETE PICTURE FROM EXISTING WORKSHOP
    @DeleteMapping("/{id}/update/delete-video/{picId}")
    public String deleteVideo(@PathVariable Long id, @PathVariable Long picId) {
        this.workshopService.deleteVideo(picId);
        return "redirect:/workshops/"+id+"/update";
    }


    //DELETE WORKSHOP
    @DeleteMapping("/{id}/delete")
    public String deleteWorkshop(@PathVariable Long id) {
        this.workshopService.deleteWorkshop(id);
        return "redirect:/mentor-profile/my-workshops";
    }

}
