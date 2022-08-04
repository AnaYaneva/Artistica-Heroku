package com.artcources.artistica.service;

import com.artcources.artistica.exception.WorkshopNotFoundException;
import com.artcources.artistica.model.entity.CourseCategoryEntity;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.enums.CourseCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.service.VideoAddServiceModel;
import com.artcources.artistica.model.service.WorkshopAddServiceModel;
import com.artcources.artistica.model.service.WorkshopUpdateServiceModel;
import com.artcources.artistica.model.service.WorkshopsAllServiceModel;
import com.artcources.artistica.model.view.WorkshopDetailsViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.repository.WorkshopCategoryRepository;
import com.artcources.artistica.repository.WorkshopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkshopService {
    private final WorkshopCategoryRepository workshopCategoryRepository;

    private final WorkshopRepository workshopRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final MentorService mentorService;
    private final VideoService videoService;

    public WorkshopService(WorkshopCategoryRepository workshopCategoryRepository, WorkshopRepository workshopRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, MentorService mentorService, VideoService videoService) {
        this.workshopCategoryRepository = workshopCategoryRepository;
        this.workshopRepository = workshopRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.mentorService = mentorService;
        this.videoService = videoService;
    }

    public void init() {
        if (workshopCategoryRepository.count() !=0){
            return;
        }

        Arrays.stream(CourseCategoryEnum.values())
                .forEach(courseCategoryEnum -> {
                    CourseCategoryEntity courseCategory = new CourseCategoryEntity();
                    courseCategory.setName(courseCategoryEnum);
                    courseCategory.setDescription("Description for " + courseCategoryEnum.name());

                    workshopCategoryRepository.save(courseCategory);
                });
    }

    public Long addNewWorkshop(WorkshopAddServiceModel workshopAddServiceModel, Principal principal) {

        //map offer service to offer entity
        OnlineWorkshopEntity wokrshopToAdd = this.modelMapper.map(workshopAddServiceModel, OnlineWorkshopEntity.class);
        //wokrshopToAdd.setCategory(workshopAddServiceModel.getCategory());
        //wokrshopToAdd.setMentor(this.mentorService.findMentorByEmail(principal.getName()));
        wokrshopToAdd.setStatus(StatusEnum.PENDING);
        OnlineWorkshopEntity savedWorkshop = this.workshopRepository.save(wokrshopToAdd);
        savedWorkshop.getVideo().clear();

        //save pictures to Cloudinary,save them in repo and add in offer
        workshopAddServiceModel.getVideo(/*?*/);

        OnlineWorkshopEntity savedOfferWithPictures = this.workshopRepository.save(savedWorkshop);

        return savedOfferWithPictures.getId();
    }

    public OnlineWorkshopEntity getWokrshopById(Long id) {
        return this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());
    }


    public void rejectWorkshop(Long id) {

        OnlineWorkshopEntity offer = this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());
        offer.setStatus(StatusEnum.DECLINED);
        this.workshopRepository.save(offer);
    }

    public void approveWorkshop(Long id) {

        OnlineWorkshopEntity offer = this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());
        offer.setStatus(StatusEnum.APPROVED);
        this.workshopRepository.save(offer);
    }

    public List<WorkshopsAllServiceModel> getAllPendingWorkshopsServiceModel() {
            return this.workshopRepository.findAllByStatus(StatusEnum.PENDING)
                    .stream()
                    .map(workshop -> this.modelMapper.map(workshop,WorkshopsAllServiceModel.class))
                    .collect(Collectors.toList());
        }

    public List<OnlineWorkshopEntity> getAllRejectedWorkshops() {
        return this.workshopRepository.findAllByStatus(StatusEnum.DECLINED);
    }

    public List<WorkshopsAllViewModel> getCurrentUserWorkshops(Principal principal) {
        List<WorkshopsAllViewModel> collect = this.workshopRepository.findAllByMentor_Email(principal.getName())
                .stream()
                .map(workshop -> {
                    WorkshopsAllViewModel workshopsAllViewModel = this.modelMapper.map(workshop, WorkshopsAllViewModel.class);
                    //workshopsAllViewModel.setCategory(workshop.getCategory().getName());
                    return workshopsAllViewModel;
                })
                .collect(Collectors.toList());

        return collect;
    }

    public List<WorkshopsAllServiceModel> getAllApprovedWorkshopsServiceModel() {
        return this.workshopRepository.findAllByStatus(StatusEnum.APPROVED)
                .stream().map(offer -> this.modelMapper.map(offer,WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
    }

    public WorkshopDetailsViewModel findWorkshopViewModelById(Long id) {
        OnlineWorkshopEntity workshop = this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());

        WorkshopDetailsViewModel workshopDetailsViewModel
                = this.modelMapper.map(workshop, WorkshopDetailsViewModel.class);
       // workshopDetailsViewModel.setMentor(workshop.getMentor().getEmail());
        //workshopDetailsViewModel.setAddress(workshop.getMentor().getAddress().getAddress());
       // workshopDetailsViewModel.setCity(workshop.getSupplier().getAddress().getCity().getName());
        //workshopDetailsViewModel.setRegion(workshop.getSupplier().getAddress().getCity().getRegion().getName());

        return workshopDetailsViewModel;
    }

    public void updateWorkshop(WorkshopUpdateServiceModel workshopUpdateServiceModel, Long id) {
    }

    public void deleteVideo(Long picId) {
        this.videoService.deletePicture(picId);
    }

    public void deleteWorkshop(Long id) {
    }

    public void addNewVideo(VideoAddServiceModel videoAddServiceModel, Long id) {
    }

    public List<WorkshopsAllViewModel> findAllWorkshopsByCategoryName(CourseCategoryEnum name) {
        return workshopRepository.findAllByCategory_Name(name).stream()
                .map(p -> modelMapper.map(p,WorkshopsAllViewModel.class)).collect(Collectors.toList());
    }
}
