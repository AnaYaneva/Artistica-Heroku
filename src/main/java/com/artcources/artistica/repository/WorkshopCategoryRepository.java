package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshopCategoryRepository extends JpaRepository<WorkshopCategoryEntity, Long> {
    WorkshopCategoryEntity findByName(WorkshopCategoryEnum workshopCategoryEnum);
}
