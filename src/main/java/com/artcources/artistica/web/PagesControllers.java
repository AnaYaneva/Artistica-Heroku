package com.artcources.artistica.web;

import com.artcources.artistica.model.view.WorkshopsAllViewModel;
import com.artcources.artistica.service.WorkshopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PagesControllers {


  private final WorkshopService workshopService;

  public PagesControllers(WorkshopService workshopService) {
    this.workshopService = workshopService;
  }

  @GetMapping("/about")
  public String about() {
    return "about";
  }

  @GetMapping("/")
  public String home(Model model){
    List<WorkshopsAllViewModel> popularWorkshops = workshopService.getMostPopular();

    model.addAttribute("workshops", popularWorkshops);
    model.addAttribute("title", "Our popular wokrshops");
    return "index";
  }

}
