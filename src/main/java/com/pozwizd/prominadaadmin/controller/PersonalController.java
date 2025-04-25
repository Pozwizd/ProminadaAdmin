package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.models.personal.PersonalResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.PersonalServiceImp;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/personal")
@AllArgsConstructor
public class PersonalController {

    private final PersonalServiceImp personalServiceImp;
    private final PersonalMapper personalMapper;

    @GetMapping
    public ModelAndView showUsersPage(Model model) {

        model.addAttribute("pageTitle", "Personal");
        model.addAttribute("pageActive", "personal");

        return new ModelAndView("personal/personal");
    }


    @GetMapping("/getAllPersonal")
    public @ResponseBody Page<PersonalTableResponse> getPageablePersonal(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(required = false, name = "roleName") String role, // Changed name to roleName
                                                                         @RequestParam(required = false, name = "filter_surname") String surname,
                                                                         @RequestParam(required = false, name = "filter_name") String name,
                                                                         @RequestParam(required = false, name = "filter_lastName") String lastName,
                                                                         @RequestParam(required = false, name = "filter_phoneNumber") String phoneNumber,
                                                                         @RequestParam(required = false, name = "filter_email") String email,
                                                                         @RequestParam(defaultValue = "10") Integer size) {
        return personalServiceImp.getPageablePersonal(page, size, surname, name, lastName, phoneNumber, email, role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePersonal(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String email = personalServiceImp.getPersonalById(id).getEmail();
        if (userDetails.getUsername().equals(email)) {
            return ResponseEntity.badRequest().build();
        }

        personalServiceImp.deletePersonal(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "personal.editUser");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("isEdit", true);
        return new ModelAndView("personal/personalForm");
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(Model model) {
        model.addAttribute("pageTitle", "personal.createUser");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("isEdit", false);
        return new ModelAndView("personal/personalForm");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PersonalResponse getPersonalProfile(@PathVariable Long id) {
        Personal personal = personalServiceImp.getPersonalById(id);
        return personalMapper.toPersonalProfileResponse(personal);
    }

    @GetMapping("/getRoles")
    public @ResponseBody List<Role> getRoles() {
        return List.of(Role.values());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePersonal(@Valid @ModelAttribute PersonalRequest personalRequest, @PathVariable Long id) {

        if (personalRequest.getId() != null && !personalRequest.getId().equals(id)) {
            return ResponseEntity.badRequest().body("ID в пути и в теле запроса не совпадают");
        }

        personalServiceImp.updatePersonal(personalRequest);

        return ResponseEntity.ok("Пользователь успешно обновлен");
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public ResponseEntity<?> createPersonal(@ModelAttribute PersonalRequest personalRequest) {

        personalServiceImp.saveFromRequest(personalRequest);
        return ResponseEntity.ok("Пользователь успешно создан");
    }
}