package com.artcources.artistica.web;

import com.artcources.artistica.config.CloudinaryConfig;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.repository.UserRepository;
import com.artcources.artistica.repository.UserRoleRepository;
import com.artcources.artistica.repository.WorkshopCategoryRepository;
import com.artcources.artistica.repository.WorkshopRepository;
import com.artcources.artistica.service.WorkshopService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkshopControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CloudinaryConfig config;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkshopCategoryRepository workshopCategoryRepository;
    @Autowired
    private WorkshopRepository workshopRepository;
    @Autowired
    private WorkshopService workshopService;


    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void allWorkshopsTest() throws Exception {
        mockMvc.perform(get("/workshops/all")).andExpect(status().isOk())
            .andExpect(view().name("workshops-all"));
    }

    @Test
    void workshopsSearchTest() throws Exception {
        mockMvc.perform(get("/workshops/search")).andExpect(status().isOk())
                .andExpect(view().name("search"));
    }

    @Test
    void allWorkshopsByCategoryTest() throws Exception {
        mockMvc.perform(get("/workshops/categories/watercolor"))
                .andExpect(view().name("workshops-all-by-criteria"));
    }

    @Test
    void allWorkshopsByExperienceLevelTest() throws Exception {
        mockMvc.perform(get("/workshops/experienceLevel/beginner"))
                .andExpect(view().name("workshops-all-by-criteria"));
    }

    private UserEntity getTestMentor() {
        UserRoleEntity mentorRole = userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.MENTOR));
        getTestWorkshop();
        UserEntity testMentor = new UserEntity().
                setUserRoles(List.of(mentorRole)).
                setFirstName("Mentor").
                setLastName("Mentorov").
                setUsername("mentor@example.com").
                setPassword("123456");
        return this.userRepository.save(testMentor);
    }

    private OnlineWorkshopEntity getTestWorkshop() {
        WorkshopCategoryEntity category=new WorkshopCategoryEntity()
                .setName(WorkshopCategoryEnum.ACRYLIC);
        WorkshopCategoryEntity savedCategory = this.workshopCategoryRepository.save(category);

        OnlineWorkshopEntity offer=new OnlineWorkshopEntity()
                .setCategory(savedCategory)
                .setDescription("")
                .setName("")
                .setStatus(StatusEnum.PENDING)
                .setMentor(this.userRepository.findByUsername("mentor@example.com").orElse(null));
        return this.workshopRepository.save(offer);
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
