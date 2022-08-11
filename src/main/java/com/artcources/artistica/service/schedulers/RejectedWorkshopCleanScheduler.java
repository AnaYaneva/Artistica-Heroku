package com.artcources.artistica.service.schedulers;


import com.artcources.artistica.service.WorkshopService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RejectedWorkshopCleanScheduler {

    private final WorkshopService workshopService;

    public RejectedWorkshopCleanScheduler(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanRejectedWorkshopsNotUpdated30Days() {
        this.workshopService.getAllRejectedWorkshops()
                .forEach(workshop -> {
                        this.workshopService.deleteWorkshop(workshop.getId());
                });
    }
}
