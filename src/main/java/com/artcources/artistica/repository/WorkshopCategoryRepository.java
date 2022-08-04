package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.CourseCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshopCategoryRepository extends JpaRepository<CourseCategoryEntity, Long> {
}
