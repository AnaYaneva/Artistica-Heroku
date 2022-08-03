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

//    @Scheduled(cron = "00 00 00 * * *")
//    public void cleanRejectedOffersNotUpdated30Days() {
//        this.workshopService.getAllRejectedOffers()
//                .forEach(offer -> {
//                    LocalDate created = offer.getCreated();
//                    LocalDate expiredDate = created.plusMonths(1L);
//                    if (expiredDate.equals(LocalDate.now())) {
//                        this.workshopService.deleteOffer(offer.getId());
//                    }
//                });
//    }
}
