package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkshopRepository  extends JpaRepository<OnlineWorkshopEntity, Long> {
    List<OnlineWorkshopEntity> findAllByStatus(StatusEnum status);

    List<OnlineWorkshopEntity> findAllByCategory_Name(WorkshopCategoryEnum categoryName);

    List<OnlineWorkshopEntity> findAllByMentor_Username(String email);

    List<OnlineWorkshopEntity> findAllByCategoryNameAndStatus(WorkshopCategoryEnum categoryName, StatusEnum status);

    // Optional<OnlineWorkshopEntity> findWorkshopByAbout(String about);
}
