package com.artcources.artistica.service;

import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.UserProfileUpdateServiceModel;
import com.artcources.artistica.model.service.UserServiceModel;
import com.artcources.artistica.model.service.UsersAllServiceModel;
import com.artcources.artistica.model.view.UserProfileUpdateViewModel;
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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final UserRoleService userRoleService;
  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  private String adminPass;

  private final ModelMapper modelMapper;

  public UserService(UserRoleService userRoleService, UserRepository userRepository,
                     UserRoleRepository userRoleRepository,
                     PasswordEncoder passwordEncoder,
                     UserDetailsService userDetailsService,
                     @Value("${app.default.admin.password}") String adminPass, ModelMapper modelMapper)  {
    this.userRoleService = userRoleService;
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.adminPass = adminPass;
    this.modelMapper = modelMapper;
  }

  public void init() {
    if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
      UserRoleEntity adminRole = new UserRoleEntity().setName(UserRoleEnum.ADMIN);
      UserRoleEntity moderatorRole = new UserRoleEntity().setName(UserRoleEnum.MODERATOR);
      UserRoleEntity mentorRole = new UserRoleEntity().setName(UserRoleEnum.MENTOR);
      UserRoleEntity studentRole = new UserRoleEntity().setName(UserRoleEnum.USER);
      
      adminRole = userRoleRepository.save(adminRole);
      moderatorRole = userRoleRepository.save(moderatorRole);
      mentorRole = userRoleRepository.save(mentorRole);
      studentRole = userRoleRepository.save(studentRole);
      
      initAdmin(List.of(adminRole, moderatorRole, mentorRole));
      initModerator(List.of(moderatorRole, mentorRole));
      initMentor(List.of(mentorRole));
      initUser(List.of());
    }
  }

  private void initMentor(List<UserRoleEntity> mentorRole) {
  }

  private void initAdmin(List<UserRoleEntity> roles) {
    UserEntity admin = (UserEntity) new UserEntity().
        setUserRoles(roles).
        setFirstName("Admin").
        setLastName("Adminov").
        setEmail("admin@example.com").
        setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(admin);
  }

  private void initModerator(List<UserRoleEntity> roles) {
    UserEntity moderator = (UserEntity) new UserEntity().
        setUserRoles(roles).
        setFirstName("Moderator").
        setLastName("Moderatorov").
        setEmail("moderator@example.com").
        setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(moderator);
  }

  private void initUser(List<UserRoleEntity> roles) {
    UserEntity user = (UserEntity) new UserEntity().
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
    login(newUser);
  }


  private void login(UserEntity userEntity) {
    UserDetails userDetails =
            userDetailsService.loadUserByUsername(userEntity.getEmail());

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
                        .forEach(role -> usersAllServiceModel.getUserRoles().add(role.getName()));
                return usersAllServiceModel;
              })
              .collect(Collectors.toList());
    }

  public boolean existByEmail(String email) {
    return this.userRepository.existsByEmail(email);
  }

  public void makeUserAdmin(String email) {
    UserEntity user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException());
    user.getUserRoles().add(this.userRoleService.findRoleByName(UserRoleEnum.ADMIN));
    this.userRepository.save(user);
  }

  public void removeAdminRole(String email) {
    UserEntity user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException());
    if(user.getId()!=1) {
      user.getUserRoles().remove(this.userRoleService.findRoleByName(UserRoleEnum.ADMIN));
      this.userRepository.save(user);
    }
  }

  public UserProfileUpdateViewModel getUserProfileViewModelByEmail(String email) {
    UserEntity user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException());
    return this.modelMapper.map(user,UserProfileUpdateViewModel.class);
  }

  public void updateUserProfile(UserProfileUpdateServiceModel userProfileUpdateServiceModel, Principal principal) {
    UserEntity user = this.userRepository.findUserByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException());
//    user.setFirstName(userProfileUpdateServiceModel.getFirstName());
//    user.setLastName(userProfileUpdateServiceModel.getLastName());
//    user.setPartnerFirstName(userProfileUpdateServiceModel.getPartnerFirstName());
//    user.setPartnerLastName(userProfileUpdateServiceModel.getPartnerLastName());
    this.userRepository.save(user);
  }
}
