package com.artcources.artistica.repository;

import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkshopRepository  extends JpaRepository<OnlineWorkshopEntity, Long> {
    List<OnlineWorkshopEntity> findAllByStatus(StatusEnum status);

    List<OnlineWorkshopEntity> findAllByMentor_Username(String email);

    List<OnlineWorkshopEntity> findAllByCategory_NameAndStatus(WorkshopCategoryEnum categoryName, StatusEnum status);

    List<OnlineWorkshopEntity> findAllByExperienceLevel_NameAndStatus(ExperienceLevelEnum experienceLevelEnum, StatusEnum status);
    @Query("SELECT w FROM OnlineWorkshopEntity w WHERE w.status='APPROVED' ORDER BY size(w.students)  DESC")
    List<OnlineWorkshopEntity> findMostPopular();

    List<OnlineWorkshopEntity> findAllByNameLike(String keyword);

    List<OnlineWorkshopEntity> findAllByStudents_Username(String username);

}
