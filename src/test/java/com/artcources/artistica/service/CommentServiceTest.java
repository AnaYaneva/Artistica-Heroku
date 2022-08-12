package com.artcources.artistica.service;

import com.artcources.artistica.model.binding.CommentCreationBindingModel;
import com.artcources.artistica.model.entity.*;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.view.CommentDisplayViewModel;
import com.artcources.artistica.repository.CommentRepository;
import com.artcources.artistica.repository.UserRepository;
import com.artcources.artistica.repository.WorkshopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    private CommentService toTest;
    @Mock
    private WorkshopRepository workshopRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        toTest = new CommentService(workshopRepository, userRepository, commentRepository);
    }

    @Test
    void getAllCommentsForWorkshopTest() {
        OnlineWorkshopEntity testWorkshop = getTestWorkshop();
        List<CommentEntity> comments = comments();
        when(workshopRepository.findById(1l)).thenReturn(Optional.ofNullable(testWorkshop));
        when(commentRepository.findAllByWorkshop(testWorkshop)).thenReturn(Optional.of(comments));
        List<CommentDisplayViewModel> actualComments = toTest.getAllCommentsForWorkshop(1l);
        Assertions.assertFalse(actualComments.isEmpty());
        Assertions.assertEquals(comments.get(0).getText(), actualComments.get(0).getMessage());
    }

    @Test
    void createCommentTest() {
        OnlineWorkshopEntity testWorkshop = getTestWorkshop();
        UserEntity mentor = mentor();
        CommentCreationBindingModel commentDto = new CommentCreationBindingModel("mentor@example.com", 1l, "comment3");
        CommentEntity c3 = new CommentEntity();
        c3.setText("comment3");
        when(userRepository.findByUsername(mentor.getUsername())).thenReturn(Optional.of(mentor));
        when(workshopRepository.findById(commentDto.getWorkshopId())).thenReturn(Optional.ofNullable(testWorkshop));
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(c3);
        CommentDisplayViewModel comment = toTest.createComment(commentDto);
        Assertions.assertEquals(commentDto.getMessage(), comment.getMessage());
    }

    private OnlineWorkshopEntity getTestWorkshop() {
        WorkshopCategoryEntity watercolor = new WorkshopCategoryEntity().setName(WorkshopCategoryEnum.WATERCOLOR);
        ExperienceLevelEntity beginner = new ExperienceLevelEntity().setName(ExperienceLevelEnum.BEGINNER);
        OnlineWorkshopEntity workshop=new OnlineWorkshopEntity()
                .setCategory(watercolor)
                .setDescription("A course for beginners")
                .setName("beginner course")
                .setExperienceLevel(beginner)
                .setStatus(StatusEnum.PENDING)
                .setComments(new HashSet<>(comments()))
                .setMentor(mentor());
        workshop.setId(1l);
        return workshop;
    }

    private UserEntity mentor() {
        UserRoleEntity mentorRole = new UserRoleEntity().setName(UserRoleEnum.MENTOR);
        UserEntity testMentor = new UserEntity().
                setUserRoles(List.of(mentorRole)).
                setFirstName("Mentor").
                setLastName("Mentorov").
                setUsername("mentor@example.com").
                setPassword("123456");
        return testMentor;
    }

    private List<CommentEntity> comments() {
        CommentEntity comment1 = new CommentEntity();
        comment1.setApproved(true);
        comment1.setAuthor(mentor());
        comment1.setCreated(LocalDateTime.now());
        comment1.setText("comment1");
        CommentEntity comment2 = new CommentEntity();
        comment2.setApproved(true);
        comment2.setAuthor(mentor());
        comment2.setCreated(LocalDateTime.now());
        comment2.setText("comment2");
        return List.of(comment1, comment2);
    }
}
