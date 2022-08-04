package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.BaseUserEntity;
import com.artcources.artistica.repository.MentorRepository;
import com.artcources.artistica.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WPUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;

    public WPUserServiceImpl(UserRepository userRepository, MentorRepository mentorRepository) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //check if user with the given email exist
        BaseUserEntity userMentorByEmail = this.userRepository.findUserByEmail(email).orElse(null);

        //if not -> check for mentor with same email
        if(userMentorByEmail==null) {
            userMentorByEmail = this.mentorRepository.findMentorByEmail(email).orElse(null);
        }

        if (userMentorByEmail==null) {
            throw new UsernameNotFoundException("User with email "+ email + " not found!");
        }
        return mapToUserDetails(userMentorByEmail);
    }

    private UserDetails mapToUserDetails(BaseUserEntity userMentorByEmail) {
        List<GrantedAuthority> authorities =
                userMentorByEmail
                        .getUserRoles()
                        .stream()
                        .map(r-> new SimpleGrantedAuthority("ROLE_"+r.getName().name()))
                        .collect(Collectors.toList());
        return new User(userMentorByEmail.getEmail(),userMentorByEmail.getPassword(),authorities);

    }
}
