package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponseForTable;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponseForTable;
import com.pozwizd.prominadaadmin.service.CommercialPropertiesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/commercialProperty")
@AllArgsConstructor
public class CommercialPropertiesController {

    private final CommercialPropertiesService commercialPropertiesService;

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<CommercialPropertiesResponse>> createCommercialProperties(
            @Valid @ModelAttribute CommercialPropertiesRequest commercialPropertiesRequest) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(commercialPropertiesService.create(commercialPropertiesRequest)));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<CommercialPropertiesResponse>> getCommercialPropertiesById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(commercialPropertiesService.readById(id)));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<CommercialPropertiesResponse>> updateCommercialProperties(
            @PathVariable Long id, @Valid @ModelAttribute CommercialPropertiesRequest commercialPropertiesRequest) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(commercialPropertiesService.update(id, commercialPropertiesRequest)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deletePage(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(commercialPropertiesService.deleteById(id)));
    }

    @GetMapping("/getAll")
    public CompletableFuture<ResponseEntity<Page<CommercialPropertiesResponse>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") Integer size){
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(commercialPropertiesService.getPageCommercialProperties(page, size)));
    }

    @PostMapping("/getByFiltration")
    public CompletableFuture<ResponseEntity<Page<CommercialPropertiesResponseForTable>>> getCommercialPropertiesByFiltration(
            @RequestBody PropertiesFilter filter) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(commercialPropertiesService.getCommercialPropertiesByPagination(filter)));
    }


}
