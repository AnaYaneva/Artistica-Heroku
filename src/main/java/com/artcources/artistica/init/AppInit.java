package com.artcources.artistica.init;


import com.artcources.artistica.service.ExperienceLevelService;
import com.artcources.artistica.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

  private final UserService userService;
  private final ExperienceLevelService experienceLevelService;

  public AppInit(UserService userService, ExperienceLevelService experienceLevelService) {
    this.userService = userService;
    this.experienceLevelService = experienceLevelService;
  }

  @Override
  public void run(String... args) {
    userService.init();
    experienceLevelService.init();
  }
}
