package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.service.BuildingCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/builderCompany")
@RequiredArgsConstructor
public class ControllerBuilderCompany {
    private final BuildingCompanyService buildingCompanyService;

    @GetMapping("/getAll")
    public ResponseEntity<List<BuildingCompany>> getAll() {
        return new ResponseEntity<>(buildingCompanyService.getAll(), HttpStatus.OK);
    }
}
