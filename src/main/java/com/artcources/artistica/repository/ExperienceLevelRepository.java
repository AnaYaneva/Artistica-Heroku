package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceLevelRepository extends JpaRepository<ExperienceLevelEntity, Long> {
}
