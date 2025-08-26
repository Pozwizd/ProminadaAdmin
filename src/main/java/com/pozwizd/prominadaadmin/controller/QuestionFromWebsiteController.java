package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.QuestionFromWebsite;
import com.pozwizd.prominadaadmin.entity.RequestForChange;
import com.pozwizd.prominadaadmin.service.QuestionFromWebsiteService;
import com.pozwizd.prominadaadmin.service.RequestForChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller("/questionFromWebsite")
@RequiredArgsConstructor
public class QuestionFromWebsiteController {

    private final QuestionFromWebsiteService questionFromWebsiteService;

    @PostMapping(value ="/create")
    public CompletableFuture<ResponseEntity<QuestionFromWebsite>> createPage(@Valid @RequestBody QuestionFromWebsite questionFromWebsite) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(questionFromWebsiteService.create(questionFromWebsite)));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<QuestionFromWebsite>> getPageById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(questionFromWebsiteService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deletePage(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(questionFromWebsiteService.deleteById(id)));
    }

    @GetMapping("/getAll")
    public CompletableFuture<ResponseEntity<Page<QuestionFromWebsite>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") Integer size){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(questionFromWebsiteService.getByPagination(page, size)));
    }
}
