package com.artcources.artistica.init;


import com.artcources.artistica.service.WorkshopService;
import com.artcources.artistica.service.ExperienceLevelService;
import com.artcources.artistica.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

  private final UserService userService;
  private final ExperienceLevelService experienceLevelService;

  private final WorkshopService courseService;

  public AppInit(UserService userService, ExperienceLevelService experienceLevelService, WorkshopService courseService) {
    this.userService = userService;
    this.experienceLevelService = experienceLevelService;
    this.courseService = courseService;
  }

  @Override
  public void run(String... args) {
    userService.init();
    experienceLevelService.init();
    courseService.init();
  }
}
