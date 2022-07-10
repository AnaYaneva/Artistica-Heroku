package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.CourseCategoryEntity;
import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import com.artcources.artistica.model.enums.CourseCategoryEnum;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.repository.CourceCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CourseService {
    private final CourceCategoryRepository courceCategoryRepository;

    public CourseService(CourceCategoryRepository courceCategoryRepository) {
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
}
