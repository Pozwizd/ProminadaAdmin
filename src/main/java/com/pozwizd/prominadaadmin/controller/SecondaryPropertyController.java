package com.pozwizd.prominadaadmin.controller;


import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponseForTable;
import com.pozwizd.prominadaadmin.service.SecondaryPropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/secondaryProperty")
@RequiredArgsConstructor
public class SecondaryPropertyController {

    private final SecondaryPropertyService secondaryPropertyService;


    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<SecondaryPropertyResponse>> createPage(
            @Valid @ModelAttribute SecondaryPropertyRequest secondaryPropertyRequest) {
        return CompletableFuture.supplyAsync(()
                -> ResponseEntity.ok(secondaryPropertyService.create(secondaryPropertyRequest)));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<SecondaryPropertyResponse>> getPageById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(()
                -> ResponseEntity.ok(secondaryPropertyService.getSecondaryPropertyById(id)));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<SecondaryPropertyResponse>> updatePage(
            @PathVariable Long id, @Valid @ModelAttribute SecondaryPropertyRequest secondaryPropertyRequest) {
        return CompletableFuture.supplyAsync(()
                -> ResponseEntity.ok(secondaryPropertyService.updateSecondaryProperty(id, secondaryPropertyRequest)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deletePage(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(()
                -> ResponseEntity.ok(secondaryPropertyService.deleteSecondaryPropertyById(id)));
    }

    @PostMapping("/getByFiltration")
    public CompletableFuture<ResponseEntity<Page<SecondaryPropertyResponseForTable>>> getSecondaryPropertyResponseByFiltration(
            @RequestBody PropertiesFilter filter) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(secondaryPropertyService.getSecondaryPropertyResponseByPagination(filter)));
    }

    @GetMapping
    public ModelAndView showBannersPage(Model model) {
        model.addAttribute("pageTitle", "banners");
        model.addAttribute("pageActive", "banners");
        return new ModelAndView("secondaryProperty/secondaryProperty");
    }
}
