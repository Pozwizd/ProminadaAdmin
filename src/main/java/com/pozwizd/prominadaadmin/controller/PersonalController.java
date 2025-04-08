package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.models.personal.PersonalDto;
import com.pozwizd.prominadaadmin.service.PersonalService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/personal")
@AllArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    @GetMapping
    public ModelAndView showUsersPage(Model model) {

        model.addAttribute("pageTitle", "Personal");
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

    @GetMapping("/create")
    public ModelAndView showCreateForm(Model model) {
        model.addAttribute("pageTitle", "Create Personal");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("isEdit", false);
        return new ModelAndView("personal/personal-form");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Personal");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("isEdit", true);
        return new ModelAndView("personal/personal-form");
    }

    // @GetMapping("/{id}")
    // @ResponseBody
    // public ResponseEntity<PersonalDto> getPersonal(@PathVariable Long id) {
    //     return ResponseEntity.ok(personalService.getPersonalById(id));
    // }

    // @PostMapping
    // @ResponseBody
    // public ResponseEntity<?> createPersonal(@RequestBody PersonalDto personalDto) {
    //     try {
    //         PersonalDto created = personalService.createPersonal(personalDto);
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("success", true);
    //         response.put("data", created);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("success", false);
    //         response.put("message", e.getMessage());
    //         return ResponseEntity.badRequest().body(response);
    //     }
    // }

    // @PutMapping("/{id}")
    // @ResponseBody
    // public ResponseEntity<?> updatePersonal(@PathVariable Long id, @RequestBody PersonalDto personalDto) {
    //     try {
    //         PersonalDto updated = personalService.updatePersonal(id, personalDto);
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("success", true);
    //         response.put("data", updated);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("success", false);
    //         response.put("message", e.getMessage());
    //         return ResponseEntity.badRequest().body(response);
    //     }
    // }
}