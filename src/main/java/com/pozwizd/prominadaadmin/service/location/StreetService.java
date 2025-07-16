package com.pozwizd.prominadaadmin.service.location;

import com.pozwizd.prominadaadmin.entity.location.Street;

import java.util.List;

public interface StreetService {

    List<Street> getAll();

    List<Street> getPageableStreet(String street, Long cityId);
}