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

// NOTE: This is not annotated as @Service, because we will return it as a bean.
public class AppUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public AppUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    return userRepository.
            findUserByEmail(username).
        map(this::map).
        orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));
  }

  private UserDetails map(UserEntity userEntity) {
    return
        User.builder().
            username(userEntity.getEmail()).
            password(userEntity.getPassword()).
            authorities(userEntity.
                getUserRoles().
                stream().
                map(this::map).
                toList()).
            build();
  }

  private GrantedAuthority map(UserRoleEntity userRole) {
    return new SimpleGrantedAuthority("ROLE_" +
        userRole.
                getName().name());
  }
}
