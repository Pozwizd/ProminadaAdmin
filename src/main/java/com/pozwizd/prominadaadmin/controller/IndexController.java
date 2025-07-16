package com.pozwizd.prominadaadmin.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;


@Controller
public class IndexController {


    @GetMapping("/")
    public ModelAndView index() {
        Locale locale = LocaleContextHolder.getLocale();

        return new ModelAndView("index");
    }

} 