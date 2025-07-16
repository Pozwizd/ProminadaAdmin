package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.location.City;
import com.pozwizd.prominadaadmin.entity.location.District;
import com.pozwizd.prominadaadmin.entity.location.Region;
import com.pozwizd.prominadaadmin.entity.location.Street;
import com.pozwizd.prominadaadmin.repository.secondary.RegionRepository;
import com.pozwizd.prominadaadmin.service.location.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LocationController {
    private final RegionService regionService;
    private final CityService cityService;
    private final DistrictService districtService;
    private final StreetService streetService;
    private final HouseService houseService;

    @GetMapping("/region/getAll")
    public @ResponseBody List<Region> getAllRegions() {
        return regionService.getRegions().join();
    }

    @GetMapping("/region/getAllByName")
    public @ResponseBody List<Region> getAllRegions(@RequestParam(required = false, name = "filter_region") String region) {
        List<Region> regions = regionService.getPageableRegions(region);
        return regions;
    }

    @GetMapping("/city/getAll")
    public @ResponseBody List<City> getAllCities() {
        return cityService.getAll();
    }

    @GetMapping("/city/getAllByName")
    public @ResponseBody List<City> getAllCities(@RequestParam(required = false, name = "filter_city") String city,
                                                 @RequestParam(required = false, name = "filter_region") Long regionId) {
        return cityService.getPageableCities(city, regionId);
    }

    @GetMapping("/district/getAll")
    public @ResponseBody List<District> getAllDistricts() {
        return districtService.getAll();
    }

    @GetMapping("/district/getAllByName")
    public @ResponseBody List<District> getAllDistricts(@RequestParam(required = false, name = "filter_district") String district,
                                                        @RequestParam(required = false, name = "filter_city") Long cityId) {
        return districtService.getPageableDistricts(district, cityId);
    }

    @GetMapping("/street/getAll")
    public @ResponseBody List<Street> getAllStreet(){
        return streetService.getAll();
    }

    @GetMapping("/street/getAllByName")
    public @ResponseBody List<Street> getAllStreet(@RequestParam(required = false, name = "filter_street") String street,
                                                   @RequestParam(required = false, name = "filter_city") Long cityId){
        return streetService.getPageableStreet(street, cityId);
    }


}
