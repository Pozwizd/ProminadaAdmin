package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.repository.secondary.*;
import com.pozwizd.prominadaadmin.service.location.CityService;
import com.pozwizd.prominadaadmin.service.location.DistrictService;
import com.pozwizd.prominadaadmin.service.location.RegionService;
import com.pozwizd.prominadaadmin.service.location.TopozoneService;
import com.pozwizd.prominadaadmin.service.serviceImp.BranchServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DataLoaderService {


    private final DistrictRepository districtRepository;
    private final StreetRepository streetRepository;
    private final HouseRepository houseRepository;


    private final RegionService regionService;
    private final CityService cityService;


    public void loadDataFromCsv() {
        long startTime = System.currentTimeMillis();

        Map<String, Region> regionCache = new HashMap<>();
        Map<String, City> cityCache = new HashMap<>();
        Map<String, District> districtCache = new HashMap<>();
        Map<String, Street> streetCache = new HashMap<>();

        List<House> housesToSave = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream("/streetBDtest.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(
                     Objects.<InputStream>requireNonNull(is),
                     "Windows-1251"))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(";", -1);

                if (columns.length < 8) {
                    continue;
                }
                String regionName = columns[0].trim();
                String districtName = columns[2].trim();
                String cityName = columns[4].trim();
                String postalCode = columns[5].trim();
                String streetName = columns[6].trim();
                String houseNumbersRaw = columns[7].trim();

                if (regionName.isEmpty() || cityName.isEmpty() || districtName.isEmpty() || streetName.isEmpty()) {
                    continue;
                }

                Region region = regionCache.computeIfAbsent(regionName, key ->
                        regionService.getOrCreate(key).join()
                );

                String cityKey = cityName + ";" + region.getId();
                City city = cityCache.computeIfAbsent(cityKey, key ->
                        cityService.getByNameAndRegionOrCreate(cityName, region, postalCode)
                );

                String districtKey = districtName + ";" + city.getId();
                District district = districtCache.computeIfAbsent(districtKey, key ->
                        districtRepository.findByNameAndCity(districtName, city)
                                .orElseGet(() -> districtRepository.save(District.builder().name(districtName).city(city).build())
                                ));

                String streetKey = streetName + ";" + district.getId();
                Street street = streetCache.computeIfAbsent(streetKey, key ->
                        streetRepository.findByNameAndDistrict(streetName, district)
                                .orElseGet(() -> streetRepository.save(Street.builder().name(streetName).district(district).build()))
                );

                if (!houseNumbersRaw.isEmpty()) {
                    String[] houseNumbers = houseNumbersRaw.split(",");
                    for (String number : houseNumbers) {
                        String cleanNumber = number.trim();
                        if (!cleanNumber.isEmpty()) {
                            housesToSave.add(House.builder().number(cleanNumber).street(street).build());
                        }
                    }
                }
            }

            if (!housesToSave.isEmpty()) {
                System.out.println("Saving " + housesToSave.size() + " houses to the database...");
                houseRepository.saveAll(housesToSave);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Finished loading data from CSV in " + (endTime - startTime) + " ms.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading data from CSV file. Transaction will be rolled back.");
        }
    }


}
