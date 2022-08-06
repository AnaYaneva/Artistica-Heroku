package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceLevelRepository extends JpaRepository<ExperienceLevelEntity, Long> {

    ExperienceLevelEntity findByName(ExperienceLevelEnum experienceLevelEnum);
}
