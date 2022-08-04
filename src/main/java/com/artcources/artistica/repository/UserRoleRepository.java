package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findRoleByName(UserRoleEnum name);

}
