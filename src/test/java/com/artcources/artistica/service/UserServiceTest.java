package com.artcources.artistica.service;

import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.UserServiceModel;
import com.artcources.artistica.model.service.UsersAllServiceModel;
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

import java.util.*;

import static java.util.Locale.ROOT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService toTest;
    @Mock
    private EmailService emailService;
    @Mock
    private UserRoleService userRoleService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AppUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new Pbkdf2PasswordEncoder();
        toTest = new UserService(emailService, userRoleService, userRepository, passwordEncoder, userDetailsService, new ModelMapper());
    }

    @Test
    void registerAndLoginTest() {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setFirstName("Admin");
        userServiceModel.setLastName("Adminov");
        userServiceModel.setPassword("123456");
        userServiceModel.setUsername("admin123");
        Mockito.when(userRoleService.findRoleByName(UserRoleEnum.USER)).thenReturn(new UserRoleEntity().setName(UserRoleEnum.USER));
        Mockito.when(userDetailsService.loadUserByUsername("admin123")).thenReturn(new UserDetails() {
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
        InOrder inOrder = inOrder(userRepository, userDetailsService, emailService);
        toTest.registerAndLogin(userServiceModel, ROOT);
        inOrder.verify(userRepository).save(any(UserEntity.class));
        inOrder.verify(userDetailsService).loadUserByUsername("admin123");
//        inOrder.verify(emailService).sendRegistrationEmail("admin123", "Admin Adminov",  ROOT);
//        Mockito.verify(emailService, Mockito.times(1)).sendRegistrationEmail("admin123", "Admin Adminov",  ROOT);
    }

    @Test
    void findAllUsersTest() {
        UserRoleEntity adminRole = new UserRoleEntity().setName(UserRoleEnum.ADMIN);
        UserRoleEntity mentorRole = new UserRoleEntity().setName(UserRoleEnum.MENTOR);
        UserRoleEntity studentRole = new UserRoleEntity().setName(UserRoleEnum.USER);

        UserEntity mentor = new UserEntity().
                setUserRoles(List.of(mentorRole)).
                setFirstName("Mentor").
                setLastName("Mentorov").
                setUsername("mentor@example.com").
                setPassword(passwordEncoder.encode("123456"));

        UserEntity admin = new UserEntity().
                setUserRoles(List.of(adminRole, mentorRole)).
                setFirstName("Admin").
                setLastName("Adminov").
                setUsername("admin@example.com").
                setPassword(passwordEncoder.encode("123456"));

        UserEntity user =  new UserEntity().
                setUserRoles(List.of(studentRole)).
                setFirstName("User").
                setLastName("Userov").
                setUsername("user@example.com").
                setPassword(passwordEncoder.encode("123456"));

        Mockito.when(userRepository.findAll()).thenReturn(List.of(admin, mentor, user));
        List<UsersAllServiceModel> users = toTest.findAllUsers();
        Assertions.assertEquals(3, users.size());
    }

    @Test
    void existByEmailTest() {
        when(userRepository.existsByUsername("admin@example.com")).thenReturn(true);
        Assertions.assertTrue(toTest.existByEmail("admin@example.com"));
    }

    @Test
    void makeUserAdminTest() {
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
        toTest.makeUserAdmin(email);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void makeUserAdminTestNonExistent() {
        Assertions.assertThrows(UserNotFoundException.class, () ->  toTest.makeUserAdmin("non-existent@example.com"));

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
