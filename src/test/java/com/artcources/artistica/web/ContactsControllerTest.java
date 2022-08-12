package com.artcources.artistica.web;

import com.artcources.artistica.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailService emailService;

    @Test
    void testContacts() throws Exception {
        mockMvc.perform(get("/contacts")).andExpect(status().isOk())
                .andExpect(view().name("contacts"));
    }

    @Test
    @WithMockUser(value = "user@example.com",roles = "USER")
    void testPostContacts() throws Exception {
        String userName = "my name is";
        String userEmail = "what@myname.is";
        String subject = "my names who ";
        String message = "My name is (chka-chka, Slim Shady)";
        doNothing().when(emailService).sendContactEmail(userEmail, userName, subject, message);

        mockMvc.perform(post("/contacts")
                .param("name", userName)
                .param("email", userEmail)
                .param("subject", subject)
                .param("message", message)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
