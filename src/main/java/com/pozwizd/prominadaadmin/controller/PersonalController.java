package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.service.PersonalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Controller
@RequestMapping("/personal")
@AllArgsConstructor
@Slf4j
public class PersonalController {

    private final PersonalService personalService;
    private final PersonalMapper personalMapper;

    @GetMapping("/getProfile")
    public @ResponseBody ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            log.error("Попытка получить профиль без аутентификации");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Пользователь не аутентифицирован"));
        }

        try {
            Optional<Personal> personalOpt = personalService.findByEmail(userDetails.getUsername());

            if (personalOpt.isEmpty()) {
                log.error("Пользователь с email {} не найден в базе", userDetails.getUsername());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пользователь не найден"));
            }

            PersonalResponse response = personalMapper.toPersonalProfileResponse(personalOpt.get());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Ошибка при получении профиля пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ошибка при получении профиля"));
        }
    }



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public ResponseEntity<PersonalResponse> createPersonal(@Valid @ModelAttribute PersonalRequest personalRequest) {
        PersonalResponse personalResponse = personalService.saveFromRequest(personalRequest);
        log.info("Новый пользователь успешно создан с ID: {}", personalResponse.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(personalResponse);
    }

    @GetMapping("/card/{id}")
    @ResponseBody
    public ResponseEntity<?> getPersonalProfile(@PathVariable Long id) {
        Optional<Personal> personalOpt = personalService.findById(id);
        if (personalOpt.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Пользователь с ID " + id + " не найден.");
            log.error("Пользователь с ID {} не найден при запросе профиля", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        PersonalResponse response = personalMapper.toPersonalProfileResponse(personalOpt.get());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<?>> updatePersonal(
            @Valid @ModelAttribute PersonalRequest personalRequest,
            @PathVariable Long id) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                personalRequest.setId(id);

                PersonalResponse response = personalService.updatePersonal(personalRequest);

                log.info("Пользователь с ID {} успешно обновлен", id);
                return ResponseEntity.ok(response);

            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Ошибка при обновлении пользователя");
                log.error("Ошибка при обновлении пользователя с ID {}", id, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        });
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonal(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        personalService.deleteById(id);
        log.info("Пользователь с ID {} успешно удален", id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/getAllPersonal")
    public @ResponseBody Page<PersonalTableResponse> getPageablePersonal(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(required = false, name = "roleName") String role,
                                                                         @RequestParam(required = false, name = "filter_surname") String surname,
                                                                         @RequestParam(required = false, name = "filter_name") String name,
                                                                         @RequestParam(required = false, name = "filter_lastName") String lastName,
                                                                         @RequestParam(required = false, name = "filter_phoneNumber") String phoneNumber,
                                                                         @RequestParam(required = false, name = "filter_email") String email,
                                                                         @RequestParam(defaultValue = "10") Integer size) {
        return personalService.getPageablePersonal(page, size, surname, name, lastName, phoneNumber, email, role);
    }

    @GetMapping("/getRoles")
    public @ResponseBody CompletableFuture<List<Role>> getRoles() {
        return CompletableFuture.completedFuture(List.of(Role.values()));
    }

    @GetMapping
    public ModelAndView showUsersPage(Model model) {
        model.addAttribute("pageTitle", "personal.users");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("opened", true);
        return new ModelAndView("personal/personal");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "personal.editUser");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("isEdit", true);
        model.addAttribute("opened", true);
        return new ModelAndView("personal/personalForm");
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(Model model) {
        model.addAttribute("pageTitle", "personal.createUser");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("isEdit", false);
        model.addAttribute("opened", true);
        return new ModelAndView("personal/personalForm");
    }

    @GetMapping("/{id}")
    public ModelAndView showPersonalProfile(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "personal.personal");
        model.addAttribute("pageActive", "personal");
        model.addAttribute("opened", true);
        return new ModelAndView("personal/personalCard");
    }

}