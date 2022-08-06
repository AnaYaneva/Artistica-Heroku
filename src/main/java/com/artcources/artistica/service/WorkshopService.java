package com.artcources.artistica.service;

import com.artcources.artistica.exception.WorkshopNotFoundException;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.service.MediaAddServiceModel;
import com.artcources.artistica.model.service.WorkshopAddServiceModel;
import com.artcources.artistica.model.service.WorkshopUpdateServiceModel;
import com.artcources.artistica.model.service.WorkshopsAllServiceModel;
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
        if (workshopCategoryRepository.count() !=0){
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

        OnlineWorkshopEntity savedWorkshop = this.workshopRepository.save(workshopToAdd);

        //save pictures to Cloudinary,save them in repo and add in workshop
        try {
            this.mediaService.getVideoEntity(savedWorkshop, workshopAddServiceModel.getVideo());
            this.mediaService.getReferencePhotoEntity(savedWorkshop, workshopAddServiceModel.getReferencePhoto());
            this.mediaService.getFinalPhotoEntity(savedWorkshop, workshopAddServiceModel.getFinalPhoto());
        } catch (IOException e) {
            e.printStackTrace();
        }

        OnlineWorkshopEntity savedWorkshopWithPictures = this.workshopRepository.save(savedWorkshop);

        return savedWorkshopWithPictures.getId();
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
        this.mediaService.deletePicture(picId);
    }

    public void deleteWorkshop(Long id) {
    }

    public void addNewVideo(MediaAddServiceModel mediaAddServiceModel, Long id) {
    }

    public List<WorkshopsAllViewModel> findAllWorkshopsByCategoryName(WorkshopCategoryEnum name) {
        return workshopRepository.findAllByCategory_Name(name).stream()
                .map(p -> modelMapper.map(p,WorkshopsAllViewModel.class)).collect(Collectors.toList());
    }

    public List<WorkshopsAllServiceModel> getAllApprovedWorkshopsByCategory(String category) {
        return  this.workshopRepository.findAllByCategoryNameAndStatus(WorkshopCategoryEnum.valueOf(category.toUpperCase()),StatusEnum.APPROVED)
                .stream()
                .map(offer -> this.modelMapper.map(offer,WorkshopsAllServiceModel.class))
                .collect(Collectors.toList());
    }

    public boolean isCurrentUserOwner(Principal principal, Long id) {
        OnlineWorkshopEntity workshop = this.workshopRepository.findById(id).orElseThrow(() -> new WorkshopNotFoundException());
        if(principal!=null && principal.getName().equals(workshop.getMentor().getUsername())) {
            return true;
        }
        return false;
    }
}
