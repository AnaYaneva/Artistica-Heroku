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
public class MentorsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    @BeforeEach
    public void setUp() {
        getTestMentor();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "mentor@example.com",roles = "USER")
    void allMentorsTest() throws Exception {
        mockMvc.perform(get("/mentors")).andExpect(status().isOk())
                .andExpect(view().name("mentors-all"));
    }


//    @Test
//    @WithMockUser(value = "mentor@example.com",roles = "MENT0R")
    void profileTest() throws Exception {
        mockMvc.perform(get("/mentors/profile/myProfile")).andExpect(status().isOk())
                .andExpect(view().name("mentor-details"));
    }

    @Test
    @WithMockUser(value = "mentor@example.com",roles = "MENT0R")
    void usernameTest() throws Exception {
        mockMvc.perform(get("/mentors/mentor@example.com")).andExpect(status().isOk())
                .andExpect(view().name("mentor-details"));
    }

    @Test
    @WithMockUser(value = "mentor@example.com",roles = "MENT0R")
    void profileErrorTest() throws Exception {
        mockMvc.perform(get("/mentors/profile/errors")).andExpect(status().isOk())
                .andExpect(view().name("mentor-details"));
    }

    private UserEntity getTestMentor() {
        UserRoleEntity mentorRole = userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.MENTOR));
        UserEntity testMentor = new UserEntity().
                setUserRoles(List.of(mentorRole)).
                setFirstName("Mentor").
                setLastName("Mentorov").
                setUsername("mentor@example.com").
                setPassword("123456");
        return this.userRepository.save(testMentor);
    }
}
