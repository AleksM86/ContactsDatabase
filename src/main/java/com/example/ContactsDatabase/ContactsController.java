package com.example.ContactsDatabase;

import com.example.ContactsDatabase.dto.Contact;
import com.example.ContactsDatabase.service.ContactsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ContactsController {
    private final ContactsService contactsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactsService.findAll());
        return "index";
    }

    @GetMapping("/contact/create-update")
    public String showCreateUpdate(Model model) {
        model.addAttribute("contact", new Contact());
        return "create-update";
    }

    @PostMapping("/contact/create-update")
    public String save(@ModelAttribute Contact contact) {
        if (contact.getId() == null)  {
            contactsService.save(contact);
        } else {
            contactsService.update(contact);
        }
        return "redirect:/";
    }

    @GetMapping("/contact/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        contactsService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/contact/create-update/{id}")
    public String showContactById(@PathVariable Long id, Model model){
        Contact contact = contactsService.findById(id).orElse(null);
        if (contact == null){
           return "redirect:/";
        }
        model.addAttribute("contact", contact);
        return "create-update";
    }
}
