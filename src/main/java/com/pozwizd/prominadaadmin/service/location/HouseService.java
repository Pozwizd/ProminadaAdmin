package com.pozwizd.prominadaadmin.service.location;

import com.pozwizd.prominadaadmin.entity.location.House;
import com.pozwizd.prominadaadmin.entity.location.Street;

import java.util.List;

public interface HouseService {

    House findOrCreate(String number, Street street);



}