package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponseForTable;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface SecondaryPropertyService {

    SecondaryProperty create(SecondaryProperty secondaryProperty);

    SecondaryPropertyResponse create(@Valid SecondaryPropertyRequest secondaryPropertyRequest);

    SecondaryPropertyResponse getSecondaryPropertyById(Long id);

    SecondaryPropertyResponse updateSecondaryProperty(Long id, @Valid SecondaryPropertyRequest secondaryPropertyRequest);

    Boolean deleteSecondaryPropertyById(Long id);

    Page<SecondaryPropertyResponseForTable> getSecondaryPropertyResponseByPagination(PropertiesFilter filter);
}
