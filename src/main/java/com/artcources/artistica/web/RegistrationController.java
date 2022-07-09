package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.UserRegisterBindingModel;
import com.artcources.artistica.model.service.UserServiceModel;
import com.artcources.artistica.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {

  private final UserService userService;

  private final ModelMapper modelMapper;

  public RegistrationController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/users/register")
  public String register() {
    return "auth-register";
  }

  @PostMapping("/users/register")
  public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

    if(bindingResult.hasErrors() ||
            !userRegisterBindingModel.getPassword()
                    .equals(userRegisterBindingModel.getConfirmPassword())){
      redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",bindingResult);

      return "redirect:register";
    }

    userService.registerAndLogin(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

    return "redirect:login";

  }

  @ModelAttribute
  public UserRegisterBindingModel userRegisterBindingModel(){
    return new UserRegisterBindingModel();
  }
}
