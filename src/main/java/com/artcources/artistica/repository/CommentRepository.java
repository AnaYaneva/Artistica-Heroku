package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.CommentEntity;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<List<CommentEntity>> findAllByWorkshop(OnlineWorkshopEntity workshop);
}
