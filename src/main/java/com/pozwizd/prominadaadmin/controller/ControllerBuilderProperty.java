package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDtoForTable;
import com.pozwizd.prominadaadmin.service.BuilderPropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/builder")
@RequiredArgsConstructor
public class ControllerBuilderProperty {
    private final BuilderPropertyService builderPropertyService;

    @GetMapping
    public ModelAndView showUsersPage(Model model) {
        model.addAttribute("pageTitle", "Objects' builders");
        model.addAttribute("pageActive", "objects-builder");
        return new ModelAndView("builderProperty/builderProperty");
    }

    @ResponseBody
    @GetMapping("/getAllBuilders")
    public Page<BuilderPropertyDtoForTable> getPageablePersonal(@RequestParam(defaultValue = "0") int page,
                                                                @ModelAttribute BuilderPropertyDtoForTable dto,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        return builderPropertyService.getPageableBuilders(page, size, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePersonal(@PathVariable Long id) {
        builderPropertyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(Model model) {
        model.addAttribute("pageTitle", "Create Builder");
        model.addAttribute("pageActive", "builder");
        model.addAttribute("isEdit", false);
        return new ModelAndView("builderProperty/builder-add");
    }

    @SneakyThrows
    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@ModelAttribute @Valid BuilderPropertyDto dto,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Locale locale = LocaleContextHolder.getLocale();
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(),
                    messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()), null, locale)));
            return ResponseEntity
                    .status(HttpStatus.valueOf(400))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errors);
        }
        BuilderProperty product = builderPropertyService.save(dto);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Успішно збережено"
        ));
    }

    @SneakyThrows
    @PutMapping("/{id}/save")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @ModelAttribute @Valid BuilderPropertyDto dto,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Locale locale = LocaleContextHolder.getLocale();
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(),
                    messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()), null, locale)));
            return ResponseEntity
                    .status(HttpStatus.valueOf(400))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errors);
        }
        dto.setId(id);
        BuilderProperty product = builderPropertyService.save(dto);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Успішно збережено"
        ));
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Builder");
        model.addAttribute("pageActive", "builder");
        model.addAttribute("isEdit", true);
        return new ModelAndView("builderProperty/builder-edit");
    }

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test-msg")
    public ResponseEntity<String> testLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.field.empty", null, locale);
        return ResponseEntity.ok(message);
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<BuilderPropertyDto> getPersonal(@PathVariable Long id) {
        return ResponseEntity.ok(builderPropertyService.getByIdInDto(id));
    }

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
