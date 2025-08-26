package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.models.filter.RealtorFilter;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorResponse;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RealtorService {

    @Named("getRealtorByCode")
    Realtor getByCode(String code);

    Page<RealtorResponse> getPageableRealtors(RealtorFilter filter);

    Realtor create(Realtor realtor);

    RealtorResponse create(RealtorRequest realtorRequest);

    Realtor readById(Long id);

    Realtor update(Realtor realtor);

    RealtorResponse update(RealtorRequest realtorRequest, Long id);

    Boolean deleteById(Long id);

    RealtorResponse readResponseById(Long id);

    List<Realtor> findAll();
}
