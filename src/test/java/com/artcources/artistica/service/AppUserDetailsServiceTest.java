package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {

    private AppUserDetailsService toTest;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        toTest = new AppUserDetailsService(userRepository);
    }

    @Test
    void loadUserByUsernameTest() {
        String email = "admin@example.com";
        UserRoleEntity studentRole = new UserRoleEntity().setName(UserRoleEnum.USER);
        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(studentRole);
        UserEntity user =  new UserEntity().
                setUserRoles(roles).
                setFirstName("User").
                setLastName("Userov").
                setUsername(email).
                setPassword("123456");
        when(userRepository.findByUsername(email)).thenReturn(Optional.of(user));
        toTest.loadUserByUsername(email);
    }
}
