package com.artcources.artistica.web;

import com.artcources.artistica.model.entity.*;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkshopControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkshopCategoryRepository workshopCategoryRepository;
    @Autowired
    private WorkshopRepository workshopRepository;
    @Autowired
    private ExperienceLevelRepository experienceLevelRepository;

    @BeforeEach
    public void setUp() {
        getTestMentor();
        getTestUser();
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
    void allWorkshopsTest() throws Exception {
        mockMvc.perform(get("/workshops/all")).andExpect(status().isOk())
            .andExpect(view().name("workshops-all"));
    }

    @Test
    @WithMockUser(value = "user@example.com",roles = "USER")
    void workshopsSearchTest() throws Exception {
        mockMvc.perform(get("/workshops/search")).andExpect(status().isOk())
                .andExpect(view().name("search"));
    }

    @Test
    @WithMockUser(value = "user@example.com",roles = "USER")
    void testWorkshopDetails() throws Exception {
        OnlineWorkshopEntity workshop = getTestWorkshop();
        Long id=workshop.getId();
        this.mockMvc
                .perform(get("/workshops/"+id))
                .andExpect(status().isOk())
                .andExpect(view().name("workshop-details"));
    }

    @Test
    @WithMockUser(value = "mentor@example.com",roles = "MENTOR")
    void testWorkshopUpdate() throws Exception {
        OnlineWorkshopEntity workshop = getTestWorkshop();
        Long id=workshop.getId();
        this.mockMvc
                .perform(get("/workshops/"+id+"/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("workshop-update"));
    }

    @Test
    @WithMockUser(value = "mentor@example.com",roles = "MENTOR")
    void testWorkshopPatch() throws Exception {
        OnlineWorkshopEntity workshop = getTestWorkshop();
        Long id=workshop.getId();
        mockMvc.perform(patch("/workshops/"+id+"/update")
                .param("Description", "an intermediate course")
                .param("Name", "Intermediate course")
                .param("category", WorkshopCategoryEnum.WATERCOLOR.name())
                .param("experienceLevel", ExperienceLevelEnum.INTERMEDIATE.name())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/workshops/"+id));

        OnlineWorkshopEntity updatedWorkshop = workshopRepository.findById(id).get();
        Assertions.assertEquals("an intermediate course", updatedWorkshop.getDescription());
        Assertions.assertEquals("Intermediate course", updatedWorkshop.getName());
        Assertions.assertEquals(WorkshopCategoryEnum.WATERCOLOR, updatedWorkshop.getCategory().getName());
        Assertions.assertEquals(ExperienceLevelEnum.INTERMEDIATE, updatedWorkshop.getExperienceLevel().getName());
    }

    @Test
    @WithMockUser(value = "mentor@example.com",roles = "MENTOR")
    void testWorkshopPatchWithErrors() throws Exception {
        OnlineWorkshopEntity workshop = getTestWorkshop();
        Long id=workshop.getId();
        mockMvc.perform(patch("/workshops/"+id+"/update")
                        .param("Description", "an intermediate course")
                        .param("Name", "Intermediate course")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workshops/"+id+"/update/errors"));

        OnlineWorkshopEntity updatedWorkshop = workshopRepository.findById(id).get();
        Assertions.assertEquals("A course for beginners", updatedWorkshop.getDescription());
        Assertions.assertEquals("beginner course", updatedWorkshop.getName());
    }


    @Test
    @WithMockUser(value = "mentor@example.com",roles = "MENTOR")
    void testWorkshopAdd() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src","test","resources","image.jpg"));
        MockMultipartFile referencePhoto = new MockMultipartFile("referencePhoto","photo.jpeg",MediaType.IMAGE_JPEG_VALUE, bytes);
        MockMultipartFile finalPhoto = new MockMultipartFile("finalPhoto","photo.jpeg",MediaType.IMAGE_JPEG_VALUE, bytes);
        MockMultipartFile video = new MockMultipartFile("video","video.jpeg",MediaType.IMAGE_JPEG_VALUE, bytes);
        mockMvc.perform(multipart("/workshops/add")
                        .file(referencePhoto)
                        .file(finalPhoto)
                        .file(video)
                        //.param("id", "1000")
                        .param("Description", "an intermediate course")
                        .param("Name", "Intermediate course")
                        .param("category", WorkshopCategoryEnum.WATERCOLOR.name())
                        .param("experienceLevel", ExperienceLevelEnum.INTERMEDIATE.name())
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workshops/" + 5));
    }

    @Test
    @WithMockUser(value = "mentor@example.com",roles = "MENTOR")
    void testWorkshopUpdateErrors() throws Exception {
        OnlineWorkshopEntity workshop = getTestWorkshop();
        Long id=workshop.getId();
        this.mockMvc
                .perform(get("/workshops/"+id+"/update/errors"))
                .andExpect(status().isOk())
                .andExpect(view().name("workshop-update"));
    }

//    @Test
//    @WithMockUser(value = "mentor@example.com",roles = "MENTOR")
    void testDeleteWorkshop() throws Exception {
        OnlineWorkshopEntity workshop = getTestWorkshop();
        Long id=workshop.getId();
        UserEntity mentor = workshop.getMentor();
        mentor.getFirstName();
        this.mockMvc
                .perform(delete("/workshops/"+id+"/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("workshops-all"));
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

    private OnlineWorkshopEntity getTestWorkshop() {
        WorkshopCategoryEntity acrylic = new WorkshopCategoryEntity().setName(WorkshopCategoryEnum.ACRYLIC);
        WorkshopCategoryEntity watercolor = new WorkshopCategoryEntity().setName(WorkshopCategoryEnum.WATERCOLOR);
        WorkshopCategoryEntity savedCategory = workshopCategoryRepository.save(acrylic);
        workshopCategoryRepository.save(watercolor);
        ExperienceLevelEntity beginner = new ExperienceLevelEntity().setName(ExperienceLevelEnum.BEGINNER);
        ExperienceLevelEntity intermediate = new ExperienceLevelEntity().setName(ExperienceLevelEnum.INTERMEDIATE);
        ExperienceLevelEntity savedExpLevel = experienceLevelRepository.save(beginner);
        experienceLevelRepository.save(intermediate);
        OnlineWorkshopEntity offer=new OnlineWorkshopEntity()
                .setCategory(savedCategory)
                .setDescription("A course for beginners")
                .setName("beginner course")
                .setExperienceLevel(savedExpLevel)
                .setStatus(StatusEnum.PENDING)
                .setMentor(userRepository.findByUsername("mentor@example.com").orElse(null));
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
        UserEntity savedUser = userRepository.save(testUser);
        return savedUser;
    }

}
