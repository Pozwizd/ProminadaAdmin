package com.pozwizd.prominadaadmin.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/banner")
@AllArgsConstructor
public class BannersController {

    @GetMapping
    public ModelAndView showUsersPage(Model model) {

        model.addAttribute("pageTitle", "banners");
        model.addAttribute("pageActive", "banners");

        return new ModelAndView("banner/banners");
    }

}
