package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.models.personal.PersonalResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.service.location.CityService;
import com.pozwizd.prominadaadmin.service.PersonalService;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;


@Controller
@RequestMapping("/personal")
@AllArgsConstructor
@Slf4j
public class PersonalController {

    private final PersonalService personalService;
    private final PersonalMapper personalMapper;


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<Map<String, String>>> updatePersonal(
            @Valid @ModelAttribute PersonalRequest personalRequest,
            @PathVariable Long id) {

        if (personalRequest.getId() != null && !personalRequest.getId().equals(id)) {
            Map<String, String> errorResponse = Map.of("error", "ID в пути и в теле запроса не совпадают");
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(errorResponse));
        }

        return personalService.updatePersonalAsync(personalRequest)
                .thenApply(personal -> {
                    Map<String, String> successResponse = Map.of("message", "Пользователь успешно обновлен");
                    log.info("Пользователь с ID {} успешно обновлен", id);
                    return ResponseEntity.ok(successResponse);
                });
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


    @Async
    @GetMapping("/getRoles")
    public @ResponseBody CompletableFuture<List<Role>> getRoles() {
        return CompletableFuture.completedFuture(List.of(Role.values()));
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public ResponseEntity<Map<String, String>> createPersonal(@ModelAttribute PersonalRequest personalRequest) {
        try {
            personalService.saveFromRequest(personalRequest);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Пользователь успешно создан");
            log.info("Новый пользователь успешно создан");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ошибка при создании пользователя");
            log.error("Ошибка при создании пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePersonal(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Personal> personalOpt = personalService.findById(id);
        if (personalOpt.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User with ID " + id + " not found.");
            log.error("Attempt to delete non-existent user with ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        String email = personalOpt.get().getEmail();

        if (userDetails.getUsername().equals(email)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "You cannot delete your own account.");
            log.warn("Attempt to delete own account by user with email {}", email);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }

        personalService.deleteById(id);
        log.info("User with ID {} successfully deleted", id);
        return ResponseEntity.ok().build();
    }
}