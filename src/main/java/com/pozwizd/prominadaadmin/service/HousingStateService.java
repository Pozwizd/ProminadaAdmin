package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.HousingState;
import org.springframework.stereotype.Service;



@Service
public interface HousingStateService {

    HousingState create(HousingState housingState);

    HousingState readById(Long id);

    HousingState update(Long id, HousingState housingState);

    void delete(Long id);
}
