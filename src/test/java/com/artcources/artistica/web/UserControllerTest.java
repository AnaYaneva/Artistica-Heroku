package com.artcources.artistica.web;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.repository.UserRepository;
import com.artcources.artistica.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    @BeforeEach
    public void setUp() {
        getTestUser();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "user@example.com",roles = "USER")
    void profileTest() throws Exception {
        mockMvc.perform(get("/users/profile")).andExpect(status().isOk())
                .andExpect(view().name("user-details"));
    }

    private UserEntity getTestUser() {
        UserRoleEntity userRole = userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.USER));
        UserEntity testUser = new UserEntity().
                setUserRoles(List.of(userRole)).
                setFirstName("User").
                setLastName("Userov").
                setUsername("user@example.com").
                setPassword("123456");
        UserEntity savedUser = this.userRepository.save(testUser);
        return savedUser;
    }
}
