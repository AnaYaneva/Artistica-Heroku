package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkshopRepository  extends JpaRepository<OnlineWorkshopEntity, Long> {
    List<OnlineWorkshopEntity> findAllByStatus(StatusEnum status);

    List<OnlineWorkshopEntity> findAllByCategory_Name(WorkshopCategoryEnum categoryName);

    List<OnlineWorkshopEntity> findAllByMentor_Username(String email);

    List<OnlineWorkshopEntity> findAllByCategoryNameAndStatus(WorkshopCategoryEnum categoryName, StatusEnum status);
    @Query("SELECT w FROM OnlineWorkshopEntity w ORDER BY size(w.students) DESC")
    List<WorkshopsAllViewModel> findMostPopular();

    // Optional<OnlineWorkshopEntity> findWorkshopByAbout(String about);
}
