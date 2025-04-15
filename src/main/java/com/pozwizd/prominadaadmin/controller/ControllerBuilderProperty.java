package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.service.BuilderPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    public Page<BuilderPropertyDto> getPageablePersonal(@RequestParam(defaultValue = "0") int page,
                                                        @ModelAttribute BuilderPropertyDto dto,
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

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Builder");
        model.addAttribute("pageActive", "builder");
        model.addAttribute("isEdit", true);
        return new ModelAndView("builderProperty/builder-edit");
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
