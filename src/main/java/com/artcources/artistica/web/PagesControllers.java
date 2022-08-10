package com.artcources.artistica.web;

import com.artcources.artistica.model.view.MentorsAllViewModel;
import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.service.MentorService;
import com.artcources.artistica.service.WorkshopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PagesControllers {


  private final WorkshopService workshopService;
  private final MentorService mentorService;
  private final ModelMapper modelMapper;

  public PagesControllers(WorkshopService workshopService, MentorService mentorService, ModelMapper modelMapper) {
    this.workshopService = workshopService;
    this.mentorService = mentorService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/about")
  public String about() {
    return "about";
  }

  @GetMapping("/")
  public String home(Model model){
    List<WorkshopsAllViewModel> popularWorkshops = workshopService.getMostPopular();
    List<MentorsAllViewModel> mentorsAllViewModels = this.mentorService.findAllMentors()
            .stream()
            .map(mentorsAllServiceModel -> this.modelMapper.map(mentorsAllServiceModel, MentorsAllViewModel.class)
                    .setPhotoUrl(Optional.ofNullable(mentorsAllServiceModel.getPhotoUrl()).orElse("/images/default.jpg")))
            .collect(Collectors.toList());
    model.addAttribute("mentors", mentorsAllViewModels);
    model.addAttribute("workshops", popularWorkshops);
    model.addAttribute("title", "Our popular workshops");
    return "index";
  }

}
