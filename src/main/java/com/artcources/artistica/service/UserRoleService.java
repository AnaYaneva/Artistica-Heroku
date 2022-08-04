package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    public UserRoleEntity findRoleByName(UserRoleEnum name) {
        return this.userRoleRepository.findRoleByName(name);
    }
}
