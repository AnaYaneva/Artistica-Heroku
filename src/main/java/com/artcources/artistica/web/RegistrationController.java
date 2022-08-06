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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

  private final UserService userService;

  private final ModelMapper modelMapper;

  private LocaleResolver localeResolver;

  public RegistrationController(UserService userService, ModelMapper modelMapper, LocaleResolver localeResolver) {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.localeResolver = localeResolver;
  }

  @GetMapping("/register")
  public String registerMain() {
    return "main-register";
  }

  @GetMapping("/users/register")
  public String register() {
    return "auth-register";
  }

  @PostMapping("/users/register")
  public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {

    if(bindingResult.hasErrors() ||
            !userRegisterBindingModel.getPassword()
                    .equals(userRegisterBindingModel.getConfirmPassword())){
      redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",bindingResult);

      return "redirect:register";
    }

    userService.registerAndLogin(modelMapper.map(userRegisterBindingModel, UserServiceModel.class),localeResolver.resolveLocale(request));

    return "redirect:/";

  }

  @ModelAttribute
  public UserRegisterBindingModel userRegisterBindingModel(){
    return new UserRegisterBindingModel();
  }
}
