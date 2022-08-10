package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String email);

  boolean existsByUsername(String email);

  List<UserEntity> findAllByUserRoles_Name(UserRoleEnum roleEnum);

}
