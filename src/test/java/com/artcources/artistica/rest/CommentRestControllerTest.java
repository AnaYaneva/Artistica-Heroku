package com.artcources.artistica.rest;


import com.artcources.artistica.model.binding.CommentCreationBindingModel;
import com.artcources.artistica.model.service.CommentMessageServiceModel;
import com.artcources.artistica.model.view.CommentDisplayViewModel;
import com.artcources.artistica.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentRestControllerTest {
    private static final Long ROUTE_ID = 1L;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

//    @MockBean(name = "mockUserDetails")
//    private UserDetails userDetails;

    @Test
//    @WithUserDetails("mockUserDetails")
    public void getComments_twoCommentsExist_commentsReturnedAsJsonAndStatusIsOk() throws Exception {
        when(commentService.getAllCommentsForWorkshop(ROUTE_ID)).thenReturn(List.of(
                new CommentDisplayViewModel(1L, "John Doe", "This is comment #1"),
                new CommentDisplayViewModel(2L, "Foo Bar", "This is comment #2")
        ));

        mockMvc.perform(get("/api/" + ROUTE_ID + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorName", is("John Doe")))
                .andExpect(jsonPath("$.[0].message", is("This is comment #1")))
                .andExpect(jsonPath("$.[1].authorName", is("Foo Bar")))
                .andExpect(jsonPath("$.[1].message", is("This is comment #2")));
    }

    @Test
    @WithMockUser(username = "testUsername")
    public void createComment_sampleData_commentIsReturnedAsExpected() throws Exception {
        when(commentService.createComment(any())).thenAnswer(interaction -> {
            CommentCreationBindingModel commentCreationDto = interaction.getArgument(0);
            return new CommentDisplayViewModel(1L, commentCreationDto.getUsername(), commentCreationDto.getMessage());
        });
        CommentMessageServiceModel commentMessageDto = new CommentMessageServiceModel("This is comment #1");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/" + ROUTE_ID + "/comments")
                .content(objectMapper.writeValueAsString(commentMessageDto))
                .with(csrf())
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.message", is("This is comment #1")))
                .andExpect(jsonPath("$.authorName", is("testUsername")));
    }

}
