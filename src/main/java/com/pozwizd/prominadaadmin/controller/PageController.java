package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.page.PageResponse;
import com.pozwizd.prominadaadmin.service.PageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page")
public class PageController {

    private final PageService pageService;

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<PageResponse>> createPage(@Valid @ModelAttribute PageResponse pageResponse) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(pageService.createPage(pageResponse)));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<PageResponse>> getPageById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(pageService.getPageById(id)));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<PageResponse>> updatePage(@PathVariable Long id, @Valid @ModelAttribute PageResponse pageResponse) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(pageService.updatePage(id, pageResponse)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deletePage(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(pageService.deletePage(id)));
    }

    @GetMapping("/pages")
    public CompletableFuture<ResponseEntity<Page<PageResponse>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") Integer size){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(pageService.getPages(page, size)));
    }
}
