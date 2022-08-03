package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.CourseCategoryEntity;
import com.artcources.artistica.model.enums.CourseCategoryEnum;
import com.artcources.artistica.repository.CourceCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WorkshopService {
    private final CourceCategoryRepository courceCategoryRepository;

    public WorkshopService(CourceCategoryRepository courceCategoryRepository) {
        this.courceCategoryRepository = courceCategoryRepository;
    }

    public void init() {
        if (courceCategoryRepository.count() !=0){
            return;
        }

        Arrays.stream(CourseCategoryEnum.values())
                .forEach(courseCategoryEnum -> {
                    CourseCategoryEntity courseCategory = new CourseCategoryEntity();
                    courseCategory.setCategory(courseCategoryEnum);
                    courseCategory.setDescription("Description for " + courseCategoryEnum.name());

                    courceCategoryRepository.save(courseCategory);
                });
    }

//    public List<Offer> getAllRejectedOffers() {
//        return this.offerRepository.findAllByStatus(StatusEnum.DECLINED);
//    }
}
