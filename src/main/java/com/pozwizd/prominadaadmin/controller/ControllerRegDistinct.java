package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.service.RegDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/regDistrict")
@RequiredArgsConstructor
public class ControllerRegDistinct {
    private final RegDistrictService regDistrictService;

    @GetMapping("/getAll")
    public ResponseEntity<List<RegDistrict>> getAll() {
        return new ResponseEntity<>(regDistrictService.getAll(), HttpStatus.OK);
    }
}
