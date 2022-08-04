package com.artcources.artistica.service;

import com.artcources.artistica.exception.UserNotFoundException;
import com.artcources.artistica.model.binding.MentorProfileUpdateBindingModel;
import com.artcources.artistica.model.entity.MentorEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.model.service.MentorProfileUpdateServiceModel;
import com.artcources.artistica.model.service.MentorRegisterServiceModel;
import com.artcources.artistica.model.service.MentorsAllServiceModel;
import com.artcources.artistica.model.view.MentorProfileViewModel;
import com.artcources.artistica.repository.MentorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MentorService {
    private final MentorRepository mentorRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final WPUserServiceImpl wpUserService;

    public MentorService(MentorRepository mentorRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService, ModelMapper modelMapper, WPUserServiceImpl wpUserService) {
        this.mentorRepository = mentorRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.wpUserService = wpUserService;
    }


    public void save(MentorRegisterServiceModel mentorRegisterServiceModel) {

//        //save mentor
//        MentorEntity mentorEntity = this.modelMapper.map(mentorRegisterServiceModel, MentorEntity.class);
//        mentorEntity.setPassword(this.passwordEncoder.encode(mentorRegisterServiceModel.getPassword()));
//        mentorEntity.setRoles(Set.of(this.userRoleService.findRoleByName(UserRoleEnum.MENTOR)));
//
//        MentorEntity registeredMentor = this.mentorRepository.save(mentorEntity);
//
//        UserDetails principal = this.wpUserService.loadUserByUsername(registeredMentor.getEmail());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                principal,
//                registeredMentor.getPassword(),
//                principal.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    public MentorEntity findMentorByEmail(String email) {
        return this.mentorRepository.findMentorByEmail(email).orElseThrow(() -> new UserNotFoundException());
    }

    public void updateMentorProfile(MentorProfileUpdateBindingModel mentorProfileUpdateBindingModel, Principal principal) {
//        //map binding model to service model
//        MentorProfileUpdateServiceModel mentorProfileUpdateServiceModel
//                = this.modelMapper.map(mentorProfileUpdateBindingModel, MentorProfileUpdateServiceModel.class);
//        //map service model to mentor entity and set properties
//        MentorEntity mentor=findMentorByEmail(principal.getName());
//        mentor.setCompanyName(mentorProfileUpdateServiceModel.getCompanyName());
//        mentor.setPhoneNumber(mentorProfileUpdateServiceModel.getPhoneNumber());
//        //update address entity
//        Address address = this.addressService.getAddressById(mentor.getAddress().getId()).orElseThrow(() -> new ObjectNotFoundException());
//        address.setAddress(mentorProfileUpdateServiceModel.getAddress())
//                .setCity(this.cityService.findByName(mentorProfileUpdateServiceModel.getCityName()));
//        this.addressService.save(address);
//        //update mentor address
//        mentor.setAddress(address);
//        // save mentor
//        this.mentorRepository.save(mentor);
    }


    public List<MentorsAllServiceModel> findAllMentors() {
        return  this.mentorRepository.findAll()
                .stream()
                .map(mentor -> {
                    MentorsAllServiceModel mentorsAllServiceModel = this.modelMapper.map(mentor, MentorsAllServiceModel.class);
//                    mentor.getRoles()
//                            .forEach(role -> mentorsAllServiceModel.getRoleEnums().add(role.getName()));
                    return mentorsAllServiceModel;
                })
                .collect(Collectors.toList());
    }


    public boolean existByEmail(String email) {
        return this.mentorRepository.existsByEmail(email);
    }


    public void makeMentorAdmin(String email) {
        MentorEntity mentor = this.mentorRepository.findMentorByEmail(email).orElseThrow(() -> new UserNotFoundException());
//        mentor.getRoles().add(this.userRoleService.findRoleByName(RoleEnum.ADMIN));
        this.mentorRepository.save(mentor);
    }


    public void removeAdminRole(String email) {
        MentorEntity mentor = this.mentorRepository.findMentorByEmail(email).orElseThrow(() -> new UserNotFoundException());
//        mentor.getRoles().remove(this.userRoleService.findRoleByName(RoleEnum.ADMIN));
        this.mentorRepository.save(mentor);
    }


    public MentorProfileViewModel getMentorProfileViewModelByEmail(String email) {
        MentorProfileViewModel  mentorProfileViewModel1 = this.mentorRepository.findMentorByEmail(email)
                .map(mentor -> {
                    MentorProfileViewModel mentorProfileViewModel =
                            this.modelMapper.map(mentor, MentorProfileViewModel.class);
//                    mentorProfileViewModel.setAddress(new AddressViewModel()
//                            .setAddress(mentor.getAddress().getAddress())
//                            .setCityName(mentor.getAddress().getCity().getName()));
                    return mentorProfileViewModel;
                })
                .orElseThrow(() -> new UserNotFoundException());
        return mentorProfileViewModel1;
    }

}
