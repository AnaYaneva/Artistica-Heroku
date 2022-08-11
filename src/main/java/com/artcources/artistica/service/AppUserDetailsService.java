package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Service
// NOTE: This is not annotated as @Service, because we will return it as a bean.
public class AppUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public AppUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    System.out.printf("load method was called. User name %s", username);
    UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));

    Set<GrantedAuthority> authorities = user.
            getUserRoles().
            stream().
            map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().name())).
            collect(Collectors.toUnmodifiableSet());


    return new User(user.getUsername(), user.getPassword(), authorities);
  }

//  private UserDetails mapToUserDetails(UserEntity userMentorByEmail) {
//    List<GrantedAuthority> authorities =
//            userMentorByEmail
//                    .getUserRoles()
//                    .stream()
//                    .map(r-> new SimpleGrantedAuthority("ROLE_"+r.getName().name()))
//                    .collect(Collectors.toList());
//    return new User(userMentorByEmail.getUsername(),userMentorByEmail.getPassword(),authorities);
//
//  }


//  private UserDetails map(UserEntity userEntity) {
//    return
//        User.builder().
//            username(userEntity.getUsername()).
//            password(userEntity.getPassword()).
//            authorities(userEntity.
//                getUserRoles().
//                stream().
//                map(this::map).
//                toList()).
//            build();
//  }
//
//  private GrantedAuthority map(UserRoleEntity userRole) {
//    return new SimpleGrantedAuthority(
//        userRole.
//                getName().name());
//  }
}
