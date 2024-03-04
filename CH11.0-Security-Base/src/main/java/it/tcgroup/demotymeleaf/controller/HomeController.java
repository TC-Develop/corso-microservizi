package it.tcgroup.demotymeleaf.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String homePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", (User) authentication.getPrincipal());
        return "index";
    }

    @GetMapping(path = "/vetrina")
    public String getVetrina(){
        return "vetrina";
    }


    @GetMapping(path = "/admin")
    public String getAdmin(){
        return "admin";
    }
}
