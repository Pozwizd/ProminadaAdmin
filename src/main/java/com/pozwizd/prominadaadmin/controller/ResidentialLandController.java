package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/residentialLand")
@AllArgsConstructor
@Slf4j
public class ResidentialLandController {

    private final ResidentialLandService residentialLandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public CompletableFuture<ResponseEntity<ResidentialLandResponse>> createResidentialLand(
            @Valid @ModelAttribute ResidentialLandRequest residentialLandRequest) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(residentialLandService.saveFromRequest(residentialLandRequest)));
    }



    @GetMapping("/card/{id}")
    @ResponseBody
    public CompletableFuture<ResponseEntity<ResidentialLandResponse>> getResidentialLandProfile(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(residentialLandService.readById(id)));
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<ResidentialLandResponse>>  updateResidentialLand(
            @Valid @ModelAttribute ResidentialLandRequest residentialLandRequest,
            @PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(residentialLandService.updateResidentialLand(residentialLandRequest)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteResidentialLand(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<ResidentialLand> residentialLand = residentialLandService.findById(id);
            if (residentialLand.isPresent()) {
                residentialLandService.deleteById(id);
                log.info("Residential land with ID {} deleted successfully", id);
                return ResponseEntity.ok(true);
            } else {
                log.warn("Residential land with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
        });
    }


    @PostMapping("/getAllResidentialLandFiltered")
    public @ResponseBody DataTablesOutput<ResidentialLandTableResponse> getAllResidentialLand(@Valid @RequestBody DataTablesInput input
    ) {
        return residentialLandService.getAllByFilterDT(input);
    }

    @GetMapping
    public ModelAndView showResidentialLandPage(Model model) {
        model.addAttribute("pageTitle", "residentialLand.lands");
        model.addAttribute("pageActive", "housesAndLots");
        model.addAttribute("opened", true);
        return new ModelAndView("residentialLand/residentialLand");
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
}
