package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.models.personal.PersonalDto;
import com.pozwizd.prominadaadmin.service.PersonalService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/personal")
@AllArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    @GetMapping
    public ModelAndView showUsersPage(Model model) {

        model.addAttribute("pageTitle", "Пользователи системы");
        model.addAttribute("pageActive", "personal");

        return new ModelAndView("personal/personal");
    }

    @ResponseBody
    @GetMapping("/getAllPersonal")
    public Page<PersonalDto> getPageablePersonal(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(required = false, name = "roleName") String role, // Changed name to roleName
                                                 @RequestParam(required = false, name = "filter_surname") String surname,
                                                 @RequestParam(required = false, name = "filter_name") String name,
                                                 @RequestParam(required = false, name = "filter_lastName") String lastName,
                                                 @RequestParam(required = false, name = "filter_phoneNumber") String phoneNumber,
                                                 @RequestParam(required = false, name = "filter_email") String email,
                                                 @RequestParam(defaultValue = "10") Integer size) {

        // Pass the potentially null roleEnum to the service layer
        return personalService.getPageablePersonal(page, size, surname, name, lastName, phoneNumber, email, role);
    }
}