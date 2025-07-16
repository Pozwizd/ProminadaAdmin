package com.pozwizd.prominadaadmin.service.location.serviceImp;

import com.pozwizd.prominadaadmin.entity.location.House;
import com.pozwizd.prominadaadmin.entity.location.Street;
import com.pozwizd.prominadaadmin.repository.secondary.HouseRepository;
import com.pozwizd.prominadaadmin.service.location.HouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Override
    @Transactional
    public House findOrCreate(String number, Street street) {
        // Перевіряємо, чи існує будинок з таким номером на цій вулиці
        return houseRepository.findByNumberAndStreetId(number, street.getId())
                .orElseGet(() -> {
                    House newHouse = new House();
                    newHouse.setNumber(number);
                    newHouse.setStreet(street);
                    return houseRepository.save(newHouse);
                });
    }


}