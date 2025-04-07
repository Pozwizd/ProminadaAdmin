package com.pozwizd.prominadaadmin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class ControllerContext {

//    private final AdminUserRepository userRepository;
//    private final AdminUserMapper adminUserMapper;

    @Value("${spring.application.name}")
    private String appName;



    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("appName", appName);

    }


    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {

        if (principal != null) {
//            AdminUserProfileResponse adminUserResponse = adminUserMapper.adminUserToProfileResponse(userRepository
//                    .findByEmail(principal.getName()).orElse(new AdminUser()));
//
//            model.addAttribute("currentUser", adminUserResponse);
//
//            String email = principal.getName();
            System.out.println("Principal: " + principal.getName());

        } else {
            System.out.println("No principal");
        }
    }
}
