package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.RequestForChange;
import com.pozwizd.prominadaadmin.models.page.PageResponse;
import com.pozwizd.prominadaadmin.service.PageService;
import com.pozwizd.prominadaadmin.service.RequestForChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller("/requestForChange")
@RequiredArgsConstructor
public class RequestForChangeController {

    private final RequestForChangeService requestForChangeService;

    @PostMapping(value ="/create")
    public CompletableFuture<ResponseEntity<RequestForChange>> createPage(@Valid @RequestBody RequestForChange requestForChange) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(requestForChangeService.create(requestForChange)));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<RequestForChange>> getPageById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(requestForChangeService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deletePage(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(requestForChangeService.deleteById(id)));
    }

    @GetMapping("/getAll")
    public CompletableFuture<ResponseEntity<Page<RequestForChange>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") Integer size){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(requestForChangeService.getByPagination(page, size)));
    }
}
