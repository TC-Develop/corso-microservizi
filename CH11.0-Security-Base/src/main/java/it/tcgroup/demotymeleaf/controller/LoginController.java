package it.tcgroup.demotymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    public String getLogout(){
        return "redirect:/login";
    }

}
