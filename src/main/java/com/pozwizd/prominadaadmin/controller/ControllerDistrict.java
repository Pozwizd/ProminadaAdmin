package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/district")
@RequiredArgsConstructor
public class ControllerDistrict {
    private final DistrictService districtService;

    @GetMapping("/getAll")
    public ResponseEntity<List<District>> getAll() {
        return new ResponseEntity<>(districtService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<District>> getByCity(@RequestParam Long cityId) {
        if (cityId == null) return null;
        return new ResponseEntity<>(districtService.getAllByCityId(cityId), HttpStatus.OK);
    }
}
