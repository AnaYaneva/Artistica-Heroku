package com.artcources.artistica.service;

import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.model.binding.MentorProfileUpdateBindingModel;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.MentorServiceModel;
import com.artcources.artistica.model.service.MentorsAllServiceModel;
import com.artcources.artistica.repository.MediaRepository;
import com.artcources.artistica.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Locale.ROOT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MentorServiceTest {

    private MentorService toTest;
    @Mock
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRoleService userRoleService;
    private ModelMapper modelMapper;
    @Mock
    private AppUserDetailsService appUserDetailsService;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private EmailService emailService;
    @Mock
    private MediaService mediaService;
    @Mock
    private MediaRepository mediaRepository;

    @BeforeEach
    void setUp() {
        passwordEncoder = new Pbkdf2PasswordEncoder();
        modelMapper = new ModelMapper();
        toTest = new MentorService(userRepository, passwordEncoder, userRoleService, modelMapper, appUserDetailsService, cloudinaryService, emailService, mediaService, mediaRepository);
    }

    @Test
    void registerAndLoginTest() throws IOException {
        MentorServiceModel model = new MentorServiceModel();
        model.setFirstName("Admin");
        model.setLastName("Adminov");
        model.setPassword("123456");
        model.setUsername("admin123");
        Mockito.when(userRoleService.findRoleByName(UserRoleEnum.MENTOR)).thenReturn(new UserRoleEntity().setName(UserRoleEnum.MENTOR));
        Mockito.when(appUserDetailsService.loadUserByUsername("admin123")).thenReturn(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
            @Override
            public String getPassword() { return "123456"; }
            @Override
            public String getUsername() {
                return "admin123";
            }
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            @Override
            public boolean isAccountNonLocked() { return true; }
            @Override
            public boolean isCredentialsNonExpired() { return true; }
            @Override
            public boolean isEnabled() { return true; }
        });
        InOrder inOrder = inOrder(userRepository, emailService);
        toTest.registerAndLogin(model, ROOT);
        inOrder.verify(userRepository).save(any(UserEntity.class));
//        inOrder.verify(emailService).sendRegistrationEmail("admin123", "Admin Adminov",  ROOT);
//        Mockito.verify(emailService, Mockito.times(1)).sendRegistrationEmail("admin123", "Admin Adminov",  ROOT);
    }


    @Test
    void updateMentorProfile() {
        UserRoleEntity adminRole = new UserRoleEntity().setName(UserRoleEnum.ADMIN);
        UserRoleEntity mentorRole = new UserRoleEntity().setName(UserRoleEnum.MENTOR);
        Principal principal = () -> "admin";
        UserEntity admin = new UserEntity().
                setUserRoles(List.of(adminRole, mentorRole)).
                setFirstName("Admin").
                setLastName("Adminov").
                setUsername("admin@example.com").
                setPassword(passwordEncoder.encode("123456"));
        MentorProfileUpdateBindingModel model = new MentorProfileUpdateBindingModel();
        model.setFirstName("Admin");
        model.setLastName("Adminov");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        toTest.updateMentorProfile(model, principal);
    }

    @Test
    void findAllMentorsTest() {
        UserRoleEntity adminRole = new UserRoleEntity().setName(UserRoleEnum.ADMIN);
        UserRoleEntity mentorRole = new UserRoleEntity().setName(UserRoleEnum.MENTOR);
        UserEntity admin = new UserEntity().
                setUserRoles(List.of(adminRole, mentorRole)).
                setFirstName("Admin").
                setLastName("Adminov").
                setUsername("admin@example.com").
                setPassword(passwordEncoder.encode("123456"));
        when(userRepository.findAllByUserRoles_Name(UserRoleEnum.MENTOR)).thenReturn(List.of(admin));
        List<MentorsAllServiceModel> mentors = toTest.findAllMentors();
    }

    @Test
    void makeMentorAdminTest() {
        String email = "admin@example.com";
        UserRoleEntity studentRole = new UserRoleEntity().setName(UserRoleEnum.USER);
        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(studentRole);
        UserEntity user =  new UserEntity().
                setUserRoles(roles).
                setFirstName("User").
                setLastName("Userov").
                setUsername(email).
                setPassword(passwordEncoder.encode("123456"));
        when(userRepository.findByUsername(email)).thenReturn(Optional.of(user));
        toTest.makeMentorAdmin(email);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void makeUserAdminTestNonExistent() {
        Assertions.assertThrows(UserNotFoundException.class, () ->  toTest.makeMentorAdmin("non-existent@example.com"));

    }

    @Test
    void removeAdminRole() {
        String email = "admin@example.com";
        UserRoleEntity studentRole = new UserRoleEntity().setName(UserRoleEnum.USER);
        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(studentRole);
        UserEntity user =  new UserEntity().
                setUserRoles(roles).
                setFirstName("User").
                setLastName("Userov").
                setUsername(email).
                setPassword(passwordEncoder.encode("123456"));
        user.setId(2l);
        when(userRepository.findByUsername(email)).thenReturn(Optional.of(user));
        toTest.removeAdminRole(email);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void removeAdminRoleNonExistent() {
        Assertions.assertThrows(UserNotFoundException.class, () ->  toTest.removeAdminRole("non-existent@example.com"));
    }
}
