package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.filter.ResidentialLandFilterRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandTableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ResidentialLandService {



    Page<ResidentialLandTableResponse> getAll(String street, int page, int size);

    void save(ResidentialLand residentialLand);

    Page<ResidentialLandTableResponse> getAllByFilter(Integer page, Integer size,
                                                      ResidentialLandFilterRequest filterRequest);

    DataTablesOutput<ResidentialLandTableResponse> getAllByFilterDT(DataTablesInput input);

    Optional<ResidentialLand> findById(Long id);

    ResidentialLandResponse readById(Long id);

    void deleteById(Long id);

    ResidentialLandResponse updateResidentialLand(
            ResidentialLandRequest residentialLandRequest);

    ResidentialLandResponse saveFromRequest(ResidentialLandRequest residentialLandRequest);
}
