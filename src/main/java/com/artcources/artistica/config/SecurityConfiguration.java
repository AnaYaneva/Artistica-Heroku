package com.artcources.artistica.config;

import com.artcources.artistica.model.enums.UserRoleEnum;
import com.artcources.artistica.repository.UserRepository;
import com.artcources.artistica.service.AppUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

  //Here we have to expose 3 things:
  // 1. PasswordEncoder
  // 2. SecurityFilterChain
  // 3. UserDetailsService



  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder();
  }



  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.
        // define which requests are allowed and which not
        authorizeRequests().
        antMatchers( "/favicon.ico").permitAll().
        antMatchers("/fontawesome*").permitAll().
        // everyone can download static resources (css, js, images)
        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
        // everyone can login and register
        antMatchers("/users/login", "/users/register",  "/mentors/register", "/register").anonymous().
        // everyone can see workshops categories, contact page, about page, home page and all mentors page
        antMatchers("/", "/workshops/all", "/workshops", "/contacts", "/about","/api/**", "/mentors").permitAll().
        // everyone can see workshops details page,mentor's details page nad users profile page
        antMatchers("/mentors/{username}", "/workshops/{id}",  "/workshops/addToList", "/workshops/removeFromList", "/workshops/categories/**", "/workshops/search",  "/workshops/search/**", "/users/**").authenticated().
        // pages available only for mentors
            antMatchers("/workshops/{id}/update/**", "/workshops/{id}/delete").
            access("@webSecurity.isOwnerOfWorkshop(authentication,#id)").
        antMatchers("/mentors/profile/myProfile", "/workshops/add").hasRole(UserRoleEnum.MENTOR.name()).
        // pages available only for admins
        antMatchers("/admin/**").hasRole(UserRoleEnum.ADMIN.name()).
        // all other pages are available for logger in users
        anyRequest().
        authenticated().
    and().
        exceptionHandling().accessDeniedPage("/access-denied").
    and().
        // configuration of form login
        formLogin().
        // the custom login form
        loginPage("/users/login").
        // the name of the username form field
        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
        // the name of the password form field
         passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
         // where to go in case that the login is successful
         defaultSuccessUrl("/", true).
         // where to go in case that the login failed
         failureForwardUrl("/users/login-error").
    and().
        // configure logout
        logout().
        // which is the logout url
        logoutUrl("/users/logout").
        // invalidate the session and delete the cookies
        invalidateHttpSession(true).
        deleteCookies("JSESSIONID");


    return http.build();
  }

  @Bean
  public AppUserDetailsService userDetailsService(UserRepository userRepository) {
    return new AppUserDetailsService(userRepository);
  }


}
