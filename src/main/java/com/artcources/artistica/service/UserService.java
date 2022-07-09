package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.UserServiceModel;
import com.artcources.artistica.repository.UserRepository;
import com.artcources.artistica.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService appUserDetailsService;
  private String adminPass;

  private final ModelMapper modelMapper;

  public UserService(UserRepository userRepository,
                     UserRoleRepository userRoleRepository,
                     PasswordEncoder passwordEncoder,
                     UserDetailsService appUserDetailsService,
                     @Value("${app.default.admin.password}") String adminPass, ModelMapper modelMapper)  {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.passwordEncoder = passwordEncoder;
    this.appUserDetailsService = appUserDetailsService;
    this.adminPass = adminPass;
    this.modelMapper = modelMapper;
  }

  public void init() {
    if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
      UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
      UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);

      adminRole = userRoleRepository.save(adminRole);
      moderatorRole = userRoleRepository.save(moderatorRole);

      initAdmin(List.of(adminRole, moderatorRole));
      initModerator(List.of(moderatorRole));
      initUser(List.of());
    }
  }

  private void initAdmin(List<UserRoleEntity> roles) {
    UserEntity admin = new UserEntity().
        setUserRoles(roles).
        setFirstName("Admin").
        setLastName("Adminov").
        setEmail("admin@example.com").
        setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(admin);
  }

  private void initModerator(List<UserRoleEntity> roles) {
    UserEntity moderator = new UserEntity().
        setUserRoles(roles).
        setFirstName("Moderator").
        setLastName("Moderatorov").
        setEmail("moderator@example.com").
        setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(moderator);
  }

  private void initUser(List<UserRoleEntity> roles) {
    UserEntity user = new UserEntity().
        setUserRoles(roles).
        setFirstName("User").
        setLastName("Userov").
        setEmail("user@example.com").
        setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(user);
  }

  public void registerAndLogin(UserServiceModel userServiceModel) {
    UserEntity newUser =
            modelMapper.map(userServiceModel, UserEntity.class);
    newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

    userRepository.save(newUser);

    UserDetails userDetails =
        appUserDetailsService.loadUserByUsername(newUser.getEmail());

    Authentication auth =
        new UsernamePasswordAuthenticationToken(
            userDetails,
            userDetails.getPassword(),
            userDetails.getAuthorities()
        );

    SecurityContextHolder.
        getContext().
        setAuthentication(auth);
  }
}
