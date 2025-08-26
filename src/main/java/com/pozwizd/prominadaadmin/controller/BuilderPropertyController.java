package com.pozwizd.prominadaadmin.controller;


import com.pozwizd.prominadaadmin.models.filter.BuilderPropertyFilter;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyResponseForTable;
import com.pozwizd.prominadaadmin.models.property.builderProperty.request.BuilderPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyResponse;
import com.pozwizd.prominadaadmin.service.BuilderPropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/builder")
@RequiredArgsConstructor
@Slf4j
public class BuilderPropertyController {

    private final BuilderPropertyService builderPropertyService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<BuilderPropertyResponse>> createBuilderProperty(
            @Valid @ModelAttribute BuilderPropertyRequest builderPropertyRequest) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(builderPropertyService.createFromRequest(builderPropertyRequest)));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<BuilderPropertyResponse>> getBuilderPropertyProfile(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(builderPropertyService.readResponseById(id)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<BuilderPropertyResponse>> updateBuilderProperty(
            @Valid @ModelAttribute BuilderPropertyRequest builderPropertyRequest,
            @PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(builderPropertyService.update(builderPropertyRequest)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteBuilderProperty(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(builderPropertyService.deleteById(id)));
    }


    // TODO: Проверить каждый параметр фильтра
    @PostMapping("/getByFiltration")
    public CompletableFuture<ResponseEntity<Page<BuilderPropertyResponseForTable>>> getBuilderPropertiesByFiltration(
            @RequestBody BuilderPropertyFilter filter) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(builderPropertyService.getBuilderPropertiesByPagination(filter)));
    }
}
