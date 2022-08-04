package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.MentorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<MentorEntity, Long> {
    Optional<MentorEntity> findMentorByEmail(String email);

    boolean existsByEmail(String email);


}
