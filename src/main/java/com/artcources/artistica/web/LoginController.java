package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.UserLoginBindingModel;
import com.artcources.artistica.model.binding.UserRegisterBindingModel;
import com.artcources.artistica.service.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {
  private final UserService userService;

  public LoginController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/login")
  public String login() {
    return "auth-login";
  }

  @PostMapping("/users/login")
  public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
    if(bindingResult.hasErrors()){
      redirectAttributes.addFlashAttribute("userLoginBindingModel",userLoginBindingModel);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",bindingResult);

      return "login";
    }

    boolean login = userService.loginUser(userLoginBindingModel);

    if(!login){
      redirectAttributes.addFlashAttribute("userLoginBindingModel",userLoginBindingModel);
      redirectAttributes.addFlashAttribute("isFound",false);

      return "redirect:login";
    }

    return "redirect:/";
  }

  // NOTE: This should be post mapping!
  @PostMapping("/users/login-error")
  public String onFailedLogin(
      @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
      RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, userName);
    redirectAttributes.addFlashAttribute("bad_credentials",
        true);

    return "redirect:/users/login";
  }

  @ModelAttribute
  public UserLoginBindingModel userLoginBindingModel(){
    return new UserLoginBindingModel();
  }
}
