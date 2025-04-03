package com.pozwizd.prominadaadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/personal")
public class PersonalController {

    @GetMapping
    public String showUsersPage(Model model) {

        model.addAttribute("pageTitle", "Пользователи системы");

        return "personal";
    }
}