package com.artcources.artistica.web;

import com.artcources.artistica.model.binding.ContactsBindingModel;
import com.artcources.artistica.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ContactsController {

    private EmailService emailService;

    public ContactsController(EmailService emailService)
    {
        this.emailService = emailService;
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @PostMapping("/contacts")
    public String postContacts(@Valid ContactsBindingModel contactsBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors())
        {
            redirectAttributes.addFlashAttribute("contactsBindingModel", contactsBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactsBindingModel",bindingResult);

            return "redirect:contacts";
        }

        emailService.sendContactEmail(contactsBindingModel.getEmail(), contactsBindingModel.getName(), contactsBindingModel.getSubject(), contactsBindingModel.getMessage());

        return "redirect:/";

    }

    @ModelAttribute
    public ContactsBindingModel contactsBindingModel(){
        return new ContactsBindingModel();
    }

}
