package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.mapper.ResidentialLandMapper;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandTableResponse;
import com.pozwizd.prominadaadmin.service.ResidentialLandService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/residentialLand")
@AllArgsConstructor
@Slf4j
public class ResidentialLandController {

    private final ResidentialLandService residentialLandService;
    private final ResidentialLandMapper residentialLandMapper;

    @GetMapping
    public ModelAndView showResidentialLandPage(Model model) {
        model.addAttribute("pageTitle", "residentialLand.lands");
        model.addAttribute("pageActive", "housesAndLots");
        model.addAttribute("opened", true);
        return new ModelAndView("residentialLand/residentialLand");
    }

    @PostMapping("/getAllResidentialLandFiltered")
    public @ResponseBody DataTablesOutput<ResidentialLandTableResponse> getAllResidentialLand(@Valid @RequestBody DataTablesInput input
    ) {
        return residentialLandService.getAllByFilterDT(input);
    }



    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "residentialLand.editLand");
        model.addAttribute("pageActive", "housesAndLots");
        model.addAttribute("isEdit", true);
        model.addAttribute("opened", true);
        return new ModelAndView("residentialLand/residentialLandForm");
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(Model model) {
        model.addAttribute("pageTitle", "residentialLand.createLand");
        model.addAttribute("pageActive", "housesAndLots");
        model.addAttribute("isEdit", false);
        model.addAttribute("opened", true);
        return new ModelAndView("residentialLand/residentialLandForm");
    }

    @GetMapping("/{id}")
    public ModelAndView showResidentialLandCard(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "residentialLand.land");
        model.addAttribute("pageActive", "residentialLand");
        model.addAttribute("opened", true);
        return new ModelAndView("residentialLand/residentialLandCard");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteResidentialLand(@PathVariable Long id) {
        Optional<ResidentialLand> residentialLandOpt = residentialLandService.findById(id);
        if (residentialLandOpt.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Земельный участок с ID " + id + " не найден.");
            log.error("Попытка удаления несуществующего земельного участка с ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        residentialLandService.deleteById(id);
        log.info("Земельный участок с ID {} успешно удален", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/card/{id}")
    @ResponseBody
    public ResponseEntity<?> getResidentialLandProfile(@PathVariable Long id) {
        Optional<ResidentialLand> residentialLandOpt = residentialLandService.findById(id);
        if (residentialLandOpt.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Земельный участок с ID " + id + " не найден.");
            log.error("Земельный участок с ID {} не найден при запросе профиля", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        ResidentialLandResponse response = residentialLandMapper.toResidentialLandResponse(residentialLandOpt.get());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> updateResidentialLand(@Valid @ModelAttribute ResidentialLandRequest residentialLandRequest, @PathVariable Long id) {

        if (residentialLandRequest.getId() != null && !residentialLandRequest.getId().equals(id)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "ID в пути и в теле запроса не совпадают");
            log.warn("Попытка обновления с несоответствующим ID: {}", residentialLandRequest.getId());
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            residentialLandService.updateResidentialLand(residentialLandRequest);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Земельный участок успешно обновлен");
            log.info("Земельный участок с ID {} успешно обновлен", id);
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ошибка при обновлении земельного участка");
            log.error("Ошибка при обновлении земельного участка с ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public ResponseEntity<Map<String, String>> createResidentialLand(@ModelAttribute ResidentialLandRequest residentialLandRequest) {
        try {
            residentialLandService.saveFromRequest(residentialLandRequest);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Land plot successfully created");
            log.info("New land plot successfully created");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error while creating land plot");
            log.error("Error while creating land plot", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
