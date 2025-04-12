package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.personal.PersonalProfileResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.service.PersonalService;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller
@RequestMapping("/personal")
@AllArgsConstructor
public class PersonalController {

    private final PersonalService personalService;
    private final PersonalMapper personalMapper;

    @GetMapping
    public ModelAndView showUsersPage(Model model) {

        model.addAttribute("pageTitle", "Personal");
        model.addAttribute("pageActive", "personal");

        return new ModelAndView("personal/personal");
    }

    @ResponseBody
    @GetMapping("/getAllPersonal")
    public Page<PersonalTableResponse> getPageablePersonal(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(required = false, name = "roleName") String role, // Changed name to roleName
                                                           @RequestParam(required = false, name = "filter_surname") String surname,
                                                           @RequestParam(required = false, name = "filter_name") String name,
                                                           @RequestParam(required = false, name = "filter_lastName") String lastName,
                                                           @RequestParam(required = false, name = "filter_phoneNumber") String phoneNumber,
                                                           @RequestParam(required = false, name = "filter_email") String email,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return personalService.getPageablePersonal(page, size, surname, name, lastName, phoneNumber, email, role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePersonal(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String email = personalService.getPersonalById(id).getEmail();
        if (userDetails.getUsername().equals(email)) {
            return ResponseEntity.badRequest().build();
        }

        personalService.deletePersonal(id);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Personal");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("isEdit", true);
        return new ModelAndView("personal/personalForm");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PersonalProfileResponse getPersonalProfile(@PathVariable Long id) {
        Personal personal = personalService.getPersonalById(id);
        return personalMapper.toPersonalProfileResponse(personal);
    }

}