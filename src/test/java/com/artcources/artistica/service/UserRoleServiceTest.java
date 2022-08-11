package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static com.artcources.artistica.model.enums.UserRoleEnum.ADMIN;
import static com.artcources.artistica.model.enums.UserRoleEnum.USER;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    private UserRoleService toTest;
    @Mock
    private UserRoleRepository userRoleRepository;


    @BeforeEach
    void setUp() {
        toTest = new UserRoleService(userRoleRepository);
    }

    @Test
    void findRoleByNameAdminTest(){
        when(userRoleRepository.findRoleByName(ADMIN)).thenReturn(new UserRoleEntity().setName(ADMIN));
        UserRoleEntity role = toTest.findRoleByName(ADMIN);
        Assertions.assertEquals(ADMIN, role.getName());
    }

    @Test
    void findRoleByNameUserTest(){
        when(userRoleRepository.findRoleByName(USER)).thenReturn(new UserRoleEntity().setName(USER));
        UserRoleEntity role = toTest.findRoleByName(USER);
        Assertions.assertEquals(USER, role.getName());
    }
}
