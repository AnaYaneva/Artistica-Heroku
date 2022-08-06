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
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final AppUserDetailsService userDetailsService;
  private String adminPass;

  private final ModelMapper modelMapper;

  public UserService(EmailService emailService, UserRoleService userRoleService, UserRepository userRepository,
                     UserRoleRepository userRoleRepository,
                     PasswordEncoder passwordEncoder,
                     AppUserDetailsService userDetailsService,
                     @Value("${app.default.admin.password}") String adminPass, ModelMapper modelMapper)  {
    this.emailService = emailService;
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
      UserRoleEntity mentorRole = new UserRoleEntity().setName(UserRoleEnum.MENTOR);
      UserRoleEntity studentRole = new UserRoleEntity().setName(UserRoleEnum.USER);
      
      adminRole = userRoleRepository.save(adminRole);
      mentorRole = userRoleRepository.save(mentorRole);
      studentRole = userRoleRepository.save(studentRole);
      
      initAdmin(List.of(adminRole, mentorRole));
      initMentor(List.of(mentorRole));
      initUser(List.of());
    }
  }

  private void initMentor(List<UserRoleEntity> roles) {
    UserEntity mentor = new UserEntity().
            setUserRoles(roles).
            setFirstName("Mentor").
            setLastName("Mentorov").
            setUsername("mentor@example.com").
            setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(mentor);
  }

  private void initAdmin(List<UserRoleEntity> roles) {
    UserEntity admin = new UserEntity().
        setUserRoles(roles).
        setFirstName("Admin").
        setLastName("Adminov").
            setUsername("admin@example.com").
        setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(admin);
  }

  private void initUser(List<UserRoleEntity> roles) {
    UserEntity user =  new UserEntity().
        setUserRoles(roles).
        setFirstName("User").
        setLastName("Userov").
            setUsername("user@example.com").
        setPassword(passwordEncoder.encode(adminPass));

    userRepository.save(user);
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
                        .forEach(role -> usersAllServiceModel.getUserRoles().add(role.getName()));
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

  public UserProfileUpdateViewModel getUserProfileViewModelByEmail(String email) {
    UserEntity user = this.userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException());
    return this.modelMapper.map(user,UserProfileUpdateViewModel.class);
  }

  public void updateUserProfile(UserProfileUpdateServiceModel userProfileUpdateServiceModel, Principal principal) {
    UserEntity user = this.userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UserNotFoundException());
//    user.setFirstName(userProfileUpdateServiceModel.getFirstName());
//    user.setLastName(userProfileUpdateServiceModel.getLastName());
//    user.setPartnerFirstName(userProfileUpdateServiceModel.getPartnerFirstName());
//    user.setPartnerLastName(userProfileUpdateServiceModel.getPartnerLastName());
    this.userRepository.save(user);
  }
}
