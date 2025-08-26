package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.investor.request.InvestorPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponseForTable;
import com.pozwizd.prominadaadmin.service.InvestorPropertyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/investorProperty")
@AllArgsConstructor
@Slf4j
public class InvestorPropertyController {

    private final InvestorPropertyService investorPropertyService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<InvestorPropertyResponse>> createInvestorProperty(
            @Valid @ModelAttribute InvestorPropertyRequest investorPropertyRequest) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(investorPropertyService.createFromRequest(investorPropertyRequest)));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<InvestorPropertyResponse>> getInvestorPropertyProfile(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(investorPropertyService.readResponseById(id)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<InvestorPropertyResponse>>  updateInvestorProperty(
            @Valid @ModelAttribute InvestorPropertyRequest investorPropertyRequest,
            @PathVariable Long id) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(investorPropertyService.update(investorPropertyRequest)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteInvestorProperty(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(()
                -> ResponseEntity.ok(investorPropertyService.deleteById(id)));
    }

    @PostMapping("/getByFiltration")
    public CompletableFuture<ResponseEntity<Page<InvestorPropertyResponseForTable>>> getInvestorPropertyByFiltration(
            @RequestBody PropertiesFilter filter) {
        return CompletableFuture.supplyAsync(() ->
                ResponseEntity.ok(investorPropertyService.getInvestorPropertyByPagination(filter)));
    }

}