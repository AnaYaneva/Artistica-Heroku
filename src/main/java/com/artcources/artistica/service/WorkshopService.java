package com.artcources.artistica.service;

import com.artcources.artistica.exception.WorkshopNotFoundException;
import com.artcources.artistica.model.binding.WorkshopUpdateBindingModel;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.service.*;
import com.artcources.artistica.model.view.WorkshopDetailsViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.repository.ExperienceLevelRepository;
import com.artcources.artistica.repository.WorkshopCategoryRepository;
import com.artcources.artistica.repository.WorkshopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkshopService {
    private final WorkshopCategoryRepository workshopCategoryRepository;

    private final ExperienceLevelRepository experienceLevelRepository;

    private final WorkshopRepository workshopRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final MentorService mentorService;
    private final MediaService mediaService;

    public WorkshopService(WorkshopCategoryRepository workshopCategoryRepository, ExperienceLevelRepository experienceLevelRepository, WorkshopRepository workshopRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, MentorService mentorService, MediaService mediaService) {
        this.workshopCategoryRepository = workshopCategoryRepository;
        this.experienceLevelRepository = experienceLevelRepository;
        this.workshopRepository = workshopRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.mentorService = mentorService;
        this.mediaService = mediaService;
    }

    public void init() {
        if (workshopCategoryRepository.count() != 0) {
            return;
        }

        Arrays.stream(WorkshopCategoryEnum.values())
                .forEach(workshopCategoryEnum -> {
                    WorkshopCategoryEntity workshopCategoryEntity = new WorkshopCategoryEntity();
                    workshopCategoryEntity.setName(workshopCategoryEnum);
                    workshopCategoryEntity.setDescription("Description for " + workshopCategoryEnum.name());

                    workshopCategoryRepository.save(workshopCategoryEntity);
                });
    }

    public Long addNewWorkshop(WorkshopAddServiceModel workshopAddServiceModel, Principal principal) {

        //map workshop service to workshop entity
        OnlineWorkshopEntity workshopToAdd = this.modelMapper.map(workshopAddServiceModel, OnlineWorkshopEntity.class);
        workshopToAdd.setCategory(this.workshopCategoryRepository.findByName(workshopAddServiceModel.getCategory()));
        workshopToAdd.setExperienceLevel(this.experienceLevelRepository.findByName(workshopAddServiceModel.getExperienceLevel()));
        workshopToAdd.setMentor(this.mentorService.findMentorByEmail(principal.getName()));
        workshopToAdd.setStatus(StatusEnum.PENDING);

        try {
            this.mediaService.getVideoEntity(workshopToAdd, workshopAddServiceModel.getVideo());
            this.mediaService.getReferencePhotoEntity(workshopToAdd, workshopAddServiceModel.getReferencePhoto());
            this.mediaService.getFinalPhotoEntity(workshopToAdd,  workshopAddServiceModel.getFinalPhoto());
        } catch (IOException e) {
            e.printStackTrace();
        }
        OnlineWorkshopEntity savedWorkshop = this.workshopRepository.save(workshopToAdd);
        return savedWorkshop.getId();
    }

    public OnlineWorkshopEntity getWokrshopById(Long id) {
        OnlineWorkshopEntity workshop= this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());


        return workshop;
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
                .map(workshop -> this.modelMapper.map(workshop, WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
    }

    public List<OnlineWorkshopEntity> getAllRejectedWorkshops() {
        return this.workshopRepository.findAllByStatus(StatusEnum.DECLINED);
    }

    public List<WorkshopsAllViewModel> getCurrentUserWorkshops(Principal principal) {
        List<WorkshopsAllViewModel> collect = this.workshopRepository.findAllByMentor_Username(principal.getName())
                .stream()
                .map(workshop -> {
                    WorkshopsAllViewModel workshopsAllViewModel = this.modelMapper.map(workshop, WorkshopsAllViewModel.class);
                    //workshopsAllViewModel.setCategory(workshop.getCategory().getName());
                    return workshopsAllViewModel;
                })
                .collect(Collectors.toList());

        return collect;
    }

    public List<WorkshopsAllViewModel> getCurrentUserWorkshopsByEmail(String username) {
        List<WorkshopsAllViewModel> collect = this.workshopRepository.findAllByMentor_Username(username)
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
                .stream().map(offer -> this.modelMapper.map(offer, WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
    }

    public WorkshopDetailsViewModel findWorkshopViewModelById(Long id) {
        OnlineWorkshopEntity workshop = this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());
        WorkshopDetailsViewModel workshopDetailsViewModel = this.modelMapper.map(workshop, WorkshopDetailsViewModel.class);
        workshopDetailsViewModel.setFinalUrl(Optional.ofNullable(workshop.getFinalPhoto()).map(p->p.getUrl()).orElse("/images/default_final.jpg"));
        workshopDetailsViewModel.setReferenceUrl(Optional.ofNullable(workshop.getReferencePhoto()).map(p->p.getUrl()).orElse("/images/default_ref.jpg"));
        workshopDetailsViewModel.setVideoUrl(Optional.ofNullable(workshop.getVideo()).map(p->p.getUrl()).orElse("/images/default-video.mp4"));
        workshopDetailsViewModel.setMentor(workshop.getMentor());
        workshopDetailsViewModel.setCategory(workshop.getCategory().getName());
        workshopDetailsViewModel.setExperienceLevel(workshop.getExperienceLevel().getName());

        return workshopDetailsViewModel;
    }

    public void updateWorkshop(WorkshopUpdateBindingModel workshopUpdateBindingModel, Long id) {
        WorkshopUpdateServiceModel workshopUpdateServiceModel = this.modelMapper.map(workshopUpdateBindingModel, WorkshopUpdateServiceModel.class);
        OnlineWorkshopEntity workshop = workshopRepository.findById(id).orElseThrow();
        workshop.setName(workshopUpdateServiceModel.getName());
        workshop.setCategory(workshopCategoryRepository.findByName(workshopUpdateServiceModel.getCategory()));
        workshop.setDescription(workshopUpdateServiceModel.getDescription());
        workshop.setExperienceLevel(experienceLevelRepository.findByName(workshopUpdateServiceModel.getExperienceLevel()));


        this.workshopRepository.save(workshop);
    }

    public void deleteVideo(Long picId) {
        this.mediaService.deletePicture(picId);
    }

    public void deleteWorkshop(Long id) {
        workshopRepository.delete(workshopRepository.findById(id).orElseThrow());
    }

    public void addNewVideo(MediaAddServiceModel mediaAddServiceModel, Long id) {
    }

//    public List<WorkshopsAllViewModel> findAllWorkshopsByCategoryName(WorkshopCategoryEnum name) {
//        return workshopRepository.findAllByCategory_Name(name).stream()
//                .map(p -> modelMapper.map(p, WorkshopsAllViewModel.class)).collect(Collectors.toList());
//    }

    public List<WorkshopsAllServiceModel> getAllApprovedWorkshopsByCategory(WorkshopCategoryEnum name) {
        return this.workshopRepository.findAllByCategory_NameAndStatus(name, StatusEnum.APPROVED)
                .stream()
                .map(workshop -> this.modelMapper.map(workshop, WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
    }

    public boolean isCurrentUserOwner(Principal principal, Long id) {
        OnlineWorkshopEntity workshop = this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());
        if (principal != null && principal.getName().equals(workshop.getMentor().getUsername())) {
            return true;
        }
        return false;
    }

    public List<WorkshopsAllViewModel> getMostPopular() {
        return this.workshopRepository.findMostPopular()
                .stream()
                .map(workshop -> this.modelMapper.map(workshop, WorkshopsAllViewModel.class))
                .limit(8)
                .collect(Collectors.toList());
    }

    public List<WorkshopsAllServiceModel> getAllApprovedWorkshopsByExperienceLevel(String experienceLevel) {
        return this.workshopRepository.findAllByExperienceLevelAndStatus(ExperienceLevelEnum.valueOf(experienceLevel.toUpperCase()), StatusEnum.APPROVED)
                .stream()
                .map(workshop -> this.modelMapper.map(workshop, WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
    }
}
