package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.repository.ExperienceLevelRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExperienceLevelService {

    private final ExperienceLevelRepository experienceLevelRepository;

    public ExperienceLevelService(ExperienceLevelRepository experienceLevelRepository) {
        this.experienceLevelRepository = experienceLevelRepository;
    }

    public void init() {
        if (experienceLevelRepository.count() !=0){
            return;
        }

        Arrays.stream(ExperienceLevelEnum.values())
                .forEach(experienceLevelEnum -> {
                    ExperienceLevelEntity experienceLevel = new ExperienceLevelEntity();
                    experienceLevel.setExperienceLevel(experienceLevelEnum);
                    experienceLevel.setDescription("Description for " + experienceLevelEnum.name());

                    experienceLevelRepository.save(experienceLevel);
                });
    }
}
