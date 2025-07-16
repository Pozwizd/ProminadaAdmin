package com.pozwizd.prominadaadmin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/secondaryProperty")
@RequiredArgsConstructor
public class SecondaryPropertyController {

    @GetMapping
    public ModelAndView showBannersPage(Model model) {
        model.addAttribute("pageTitle", "banners");
        model.addAttribute("pageActive", "banners");
        return new ModelAndView("secondaryProperty/secondaryProperty");
    }
}
