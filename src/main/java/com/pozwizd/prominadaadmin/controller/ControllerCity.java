package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/city")
@RequiredArgsConstructor
public class ControllerCity {
    private final CityService cityService;

    @GetMapping("/getAll")
    public ResponseEntity<List<City>> getCities() {
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<City>> getByRegDistrict(@RequestParam Long regDistrictId) {
        return new ResponseEntity<>(cityService.getAllByRegDistrictId(regDistrictId), HttpStatus.OK);
    }
}
