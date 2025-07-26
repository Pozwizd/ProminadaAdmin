package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialProperties;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CommercialPropertiesService {

    CommercialProperties create(CommercialProperties commercialProperties);

    void delete(CommercialProperties commercialProperties);


    CommercialPropertiesResponse create(@Valid CommercialPropertiesRequest commercialPropertiesResponse);

    CommercialPropertiesResponse readById(Long id);

    CommercialPropertiesResponse update(Long id, @Valid CommercialPropertiesRequest commercialPropertiesResponse);

    Boolean deleteById(Long id);

    Page<CommercialPropertiesResponse> getPageCommercialProperties(int page, Integer size);
}
