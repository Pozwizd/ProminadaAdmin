package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.service.DistrictService;
import com.pozwizd.prominadaadmin.service.RegDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/distinct")
@RequiredArgsConstructor
public class ControllerDistinct {
    private final DistrictService districtService;

    @GetMapping("/getAll")
    public ResponseEntity<List<District>> getAll() {
        return new ResponseEntity<>(districtService.getAll(), HttpStatus.OK);
    }
}
