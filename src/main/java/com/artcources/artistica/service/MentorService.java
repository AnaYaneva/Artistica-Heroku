package com.artcources.artistica.service;

import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.model.binding.MentorProfileUpdateBindingModel;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.MentorProfileUpdateServiceModel;
import com.artcources.artistica.model.service.MentorServiceModel;
import com.artcources.artistica.model.service.MentorsAllServiceModel;
import com.artcources.artistica.model.view.MentorProfileViewModel;
import com.artcources.artistica.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final AppUserDetailsService appUserDetailsService;
    private final CloudinaryService cloudinaryService;
    private final EmailService emailService;
    private final MediaService mediaService;

    public MentorService(UserRepository mentorRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService, ModelMapper modelMapper, AppUserDetailsService appUserDetailsService, CloudinaryService cloudinaryService, EmailService emailService, MediaService mediaService) {
        this.userRepository = mentorRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.appUserDetailsService = appUserDetailsService;
        this.cloudinaryService = cloudinaryService;
        this.emailService = emailService;
        this.mediaService = mediaService;
    }


    public void registerAndLogin(MentorServiceModel mentorServiceModel, Locale preferredLocale) {
        UserEntity newUser =
                modelMapper.map(mentorServiceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(mentorServiceModel.getPassword()));
        newUser.setUserRoles(List.of(this.userRoleService.findRoleByName(UserRoleEnum.MENTOR)));
        try {
            mediaService.getPhotoEntity(newUser, mentorServiceModel.getPhoto());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(newUser);
        login(newUser);
        emailService.sendRegistrationEmail(newUser.getUsername(),
                newUser.getFirstName() + " " + newUser.getLastName(),
                preferredLocale);
    }


    private void login(UserEntity userEntity) {
        UserDetails userDetails =
                appUserDetailsService.loadUserByUsername(userEntity.getUsername());

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

    public UserEntity findMentorByEmail(String email) {
        return this.userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException());
    }

    public void updateMentorProfile(MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel, Principal principal) {
        //map binding model to service model
        MentorProfileUpdateServiceModel mentorProfileUpdateServiceModel
                = this.modelMapper.map(mentorProfileUpdateBindingModel, MentorProfileUpdateServiceModel.class);
        //map service model to mentor entity and set properties
        UserEntity mentor=findMentorByEmail(principal.getName());
        mentor.setFirstName(mentorProfileUpdateServiceModel.getFirstName());
        mentor.setLastName(mentorProfileUpdateServiceModel.getLastName());
        mentor.setFacebook(mentorProfileUpdateServiceModel.getFacebook());
        mentor.setInstagram(mentorProfileUpdateServiceModel.getInstagram());
        mentor.setLinkedIn(mentorProfileUpdateServiceModel.getLinkedIn());

        // save mentor
        this.userRepository.save(mentor);
    }


    public List<MentorsAllServiceModel> findAllMentors() {
        return  this.userRepository.findAllByUserRoles_Name(UserRoleEnum.MENTOR)
                .stream()
                .map(mentor -> {
                    MentorsAllServiceModel mentorsAllServiceModel = this.modelMapper.map(mentor, MentorsAllServiceModel.class);
                    return mentorsAllServiceModel;
                })
                .collect(Collectors.toList());
    }


    public boolean existByEmail(String email) {
        return this.userRepository.existsByUsername(email);
    }


    public void makeMentorAdmin(String email) {
        UserEntity mentor = this.userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException());
//        mentor.getRoles().add(this.userRoleService.findRoleByName(RoleEnum.ADMIN));
        this.userRepository.save(mentor);
    }


    public void removeAdminRole(String email) {
        UserEntity mentor = this.userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException());
//        mentor.getRoles().remove(this.userRoleService.findRoleByName(RoleEnum.ADMIN));
        this.userRepository.save(mentor);
    }


    public MentorProfileViewModel getMentorProfileViewModelByEmail(String email) {
        MentorProfileViewModel  mentorProfileViewModel = this.userRepository.findByUsername(email)
                .map(m -> this.modelMapper.map(m, MentorProfileViewModel.class).setPhoto(Optional.ofNullable(m.getPhoto()).map(p->p.getUrl()).orElse("/images/default.jpg")))
                .orElseThrow(() -> new UserNotFoundException());
        return mentorProfileViewModel;
    }

}
