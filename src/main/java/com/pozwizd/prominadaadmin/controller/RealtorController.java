package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.mapper.RealtorMapper;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorResponse;
import com.pozwizd.prominadaadmin.models.realtor.RealtorTableResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.RealtorServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/realtor")
@RequiredArgsConstructor
public class RealtorController {
    private final RealtorServiceImp realtorServiceImp;
    private final RealtorMapper realtorMapper;

    @GetMapping
    public ModelAndView showUsersPage(Model model) {

        model.addAttribute("pageTitle", "realtor.users");
        model.addAttribute("pageActive", "realtor");

        return new ModelAndView("realtor/realtor");
    }


    @GetMapping("/getAllRealtor")
    public @ResponseBody Page<RealtorTableResponse> getPageableRealtor(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(required = false, name = "code") String code,
                                                                        @RequestParam(required = false, name = "fullname") String fullname,
                                                                        @RequestParam(required = false, name = "email") String email,
                                                                        @RequestParam(required = false, name = "dateOfBirthday") String dateOfBirthday,
                                                                        @RequestParam(defaultValue = "10") Integer size) {
        return realtorServiceImp.getPageableRealtor(page, size, code, fullname, email, dateOfBirthday);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRealtor(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String email = realtorServiceImp.getRealtorById(id).getEmail();
        if (userDetails.getUsername().equals(email)) {
            return ResponseEntity.badRequest().build();
        }

        realtorServiceImp.deleteRealtor(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "realtor.editUser");
        model.addAttribute("pageActive", "realtor");
        model.addAttribute("isEdit", true);
        return new ModelAndView("realtor/realtorForm");
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(Model model) {
        model.addAttribute("pageTitle", "realtor.createRealtor");
        model.addAttribute("pageActive", "realtor");
        model.addAttribute("isEdit", false);
        return new ModelAndView("realtor/realtorForm");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public RealtorResponse getRealtorProfile(@PathVariable Long id) {
        Realtor realtor = realtorServiceImp.getRealtorById(id);
        return realtorMapper.toRealtorProfileResponse(realtor);
    }

    @GetMapping("/getRoles")
    public @ResponseBody List<Role> getRoles() {
        return List.of(Role.values());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateRealtor(@Valid @ModelAttribute RealtorRequest realtorRequest, @PathVariable Long id) {

        if (realtorRequest.getId() != null && !realtorRequest.getId().equals(id)) {
            return ResponseEntity.badRequest().body("ID в пути и в теле запроса не совпадают");
        }

        realtorServiceImp.updateRealtor(realtorRequest);

        return ResponseEntity.ok("Реалтор успешно обновлен");
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public ResponseEntity<?> createRealtor(@ModelAttribute RealtorRequest realtorRequest) {

        realtorServiceImp.saveFromRequest(realtorRequest);
        return ResponseEntity.ok("Реалтор успешно создан");
    }
}
