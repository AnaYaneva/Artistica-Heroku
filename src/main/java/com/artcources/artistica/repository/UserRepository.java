package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findUserByEmail(String email);

  boolean existsByEmail(String email);
}
