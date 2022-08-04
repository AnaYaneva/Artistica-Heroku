package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
}
