package com.pozwizd.prominadaadmin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class ControllerContext {



    @Value("${spring.application.name}")
    private String appName;



    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("appName", appName);

    }


    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {

        if (principal != null) {
            System.out.println("Principal: " + principal.getName());

        } else {
            System.out.println("No principal");
        }
    }
}
