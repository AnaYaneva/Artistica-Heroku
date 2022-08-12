package com.artcources.artistica.web;


import com.artcources.artistica.model.entity.*;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.repository.*;
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
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private WorkshopCategoryRepository workshopCategoryRepository;
    @Autowired
    private WorkshopRepository workshopRepository;
    @Autowired
    private ExperienceLevelRepository experienceLevelRepository;

    @BeforeEach
    public void setUp() {
        getTestAdmin();
    }

    @AfterEach
    public void tearDown() {
        workshopRepository.deleteAll();
        workshopCategoryRepository.deleteAll();
        experienceLevelRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "admin@example.com",roles = "ADMIN")
    void manageUsersTest() throws Exception {
        mockMvc.perform(get("/admin/manage-users")).andExpect(status().isOk())
                .andExpect(view().name("admin-manage-users"));
    }

    @Test
    @WithMockUser(value = "admin@example.com",roles = "ADMIN")
    void reviewWorkshopsTest() throws Exception {
        getTestWorkshop();
        mockMvc.perform(get("/admin/review-workshops")).andExpect(status().isOk())
                .andExpect(view().name("admin-approve-workshop"));
    }

    private UserEntity getTestAdmin() {
        UserRoleEntity adminRole = userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.ADMIN));
        UserEntity testMentor = new UserEntity().
                setUserRoles(List.of(adminRole)).
                setFirstName("Admin").
                setLastName("Adminov").
                setUsername("admin@example.com").
                setPassword("123456");
        return this.userRepository.save(testMentor);
    }

    private OnlineWorkshopEntity getTestWorkshop() {
        UserRoleEntity mentorRole = userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.MENTOR));
        UserEntity testMentor = new UserEntity().
                setUserRoles(List.of(mentorRole)).
                setFirstName("Mentor").
                setLastName("Mentorov").
                setUsername("mentor@example.com").
                setPassword("123456");
        userRepository.save(testMentor);

        WorkshopCategoryEntity category = new WorkshopCategoryEntity().setName(WorkshopCategoryEnum.ACRYLIC);
        WorkshopCategoryEntity savedCategory = workshopCategoryRepository.save(category);
        ExperienceLevelEntity experienceLevel = new ExperienceLevelEntity().setName(ExperienceLevelEnum.BEGINNER);
        ExperienceLevelEntity savedExpLevel = experienceLevelRepository.save(experienceLevel);
        OnlineWorkshopEntity workshop = new OnlineWorkshopEntity()
                .setCategory(savedCategory)
                .setDescription("A course for beginners")
                .setName("beginner course")
                .setExperienceLevel(savedExpLevel)
                .setStatus(StatusEnum.PENDING)
                .setMentor(userRepository.findByUsername("mentor@example.com").orElse(null));
        return this.workshopRepository.save(workshop);
    }
}
