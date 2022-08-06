package com.artcources.artistica.config;

import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.service.WorkshopService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {

    private final WorkshopService workshopService;


    public WebSecurity(WorkshopService workshopService) {

        this.workshopService = workshopService;
    }


    public boolean isOwnerOfWorkshop(Authentication authentication, Long id) {
        OnlineWorkshopEntity workshop=this.workshopService.getWokrshopById(id);

        if (workshop.getMentor().getUsername().equals(authentication.getName())) {
            return true;
        }
        return false;
    }


}
