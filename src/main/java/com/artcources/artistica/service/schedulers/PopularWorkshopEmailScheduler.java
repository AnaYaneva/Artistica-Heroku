package com.artcources.artistica.service.schedulers;


import com.artcources.artistica.service.EmailService;
import com.artcources.artistica.service.UserService;
import com.artcources.artistica.service.WorkshopService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PopularWorkshopEmailScheduler {

    private final WorkshopService workshopService;
    private final EmailService emailService;
    private final UserService userService;

    public PopularWorkshopEmailScheduler(WorkshopService workshopService, EmailService emailService, UserService userService) {
        this.workshopService = workshopService;
        this.emailService = emailService;
        this.userService = userService;
    }

//    @Scheduled(cron = "0 0 0 * * 1")
    @Scheduled(cron = "0 0 * * * 1")
    public void cleanRejectedWorkshopsNotUpdated30Days() {
        this.userService.getAllUsers().forEach(user -> {
            emailService.sendWeeklyEmail(user.getUsername(), user.toString());
        });

    }
}
