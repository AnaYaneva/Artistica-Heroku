package com.artcources.artistica.service;

import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.UserProfileUpdateServiceModel;
import com.artcources.artistica.model.service.UserServiceModel;
import com.artcources.artistica.model.service.UsersAllServiceModel;
import com.artcources.artistica.model.view.UserProfileViewModel;
import com.artcources.artistica.repository.UserRepository;
import com.artcources.artistica.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final EmailService emailService;
  private final UserRoleService userRoleService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AppUserDetailsService userDetailsService;

  private final ModelMapper modelMapper;

  public UserService(EmailService emailService, UserRoleService userRoleService, UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     AppUserDetailsService userDetailsService,
                      ModelMapper modelMapper)  {
    this.emailService = emailService;
    this.userRoleService = userRoleService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.modelMapper = modelMapper;
  }


  public void registerAndLogin(UserServiceModel userServiceModel, Locale preferredLocale) {
    UserEntity newUser =
            modelMapper.map(userServiceModel, UserEntity.class);
    newUser.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
    newUser.setUserRoles(List.of(this.userRoleService.findRoleByName(UserRoleEnum.USER)));

    userRepository.save(newUser);
    login(newUser);
    emailService.sendRegistrationEmail(newUser.getUsername(),
            newUser.getFirstName() + " " + newUser.getLastName(),
            preferredLocale);
  }


  private void login(UserEntity userEntity) {
    UserDetails userDetails =
            userDetailsService.loadUserByUsername(userEntity.getUsername());

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

    public List<UsersAllServiceModel> findAllUsers() {
      return  this.userRepository.findAll()
              .stream()
              .map(user -> {
                UsersAllServiceModel usersAllServiceModel = this.modelMapper.map(user, UsersAllServiceModel.class);
                user.getUserRoles()
                        .forEach(role -> usersAllServiceModel.getUserRoles().add(role));
                return usersAllServiceModel;
              })
              .collect(Collectors.toList());
    }

  public boolean existByEmail(String email) {
    return this.userRepository.existsByUsername(email);
  }

  public void makeUserAdmin(String email) {
    UserEntity user = this.userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException());
    user.getUserRoles().add(this.userRoleService.findRoleByName(UserRoleEnum.ADMIN));
    this.userRepository.save(user);
  }

  public void removeAdminRole(String email) {
    UserEntity user = this.userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException());
    if(user.getId()!=1) {
      user.getUserRoles().remove(this.userRoleService.findRoleByName(UserRoleEnum.ADMIN));
      this.userRepository.save(user);
    }
  }

  public UserProfileViewModel getUserProfileViewModelByEmail(String email) {
    UserEntity user = this.userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException());
    return this.modelMapper.map(user, UserProfileViewModel.class);
  }

  public void updateUserProfile(UserProfileUpdateServiceModel userProfileUpdateServiceModel, Principal principal) {
    UserEntity user = this.userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UserNotFoundException());
    user.setFirstName(userProfileUpdateServiceModel.getFirstName());
    user.setLastName(userProfileUpdateServiceModel.getLastName());

    this.userRepository.save(user);
  }
}
