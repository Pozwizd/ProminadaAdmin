package com.pozwizd.prominadaadmin.controller;


import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import com.pozwizd.prominadaadmin.service.DistrictService;
import com.pozwizd.prominadaadmin.service.TopozoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/topozone")
@RequiredArgsConstructor
public class ControllerTopozone {
    private final TopozoneService topozoneService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Topozone>> getAll() {
        return new ResponseEntity<>(topozoneService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Topozone>> getByDistrict(@RequestParam Long districtId) {
        return new ResponseEntity<>(topozoneService.getAllByDistrictId(districtId), HttpStatus.OK);
    }
}
