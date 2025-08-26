package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import com.pozwizd.prominadaadmin.models.filter.BuilderPropertyFilter;
import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.investor.request.InvestorPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponseForTable;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface InvestorPropertyService {

    InvestorProperty create(InvestorProperty investorProperty);

    InvestorProperty readById(Long id);

    InvestorPropertyResponse createFromRequest(@Valid InvestorPropertyRequest investorPropertyRequest);

    InvestorPropertyResponse readResponseById(Long id);

    InvestorPropertyResponse update(@Valid InvestorPropertyRequest investorPropertyRequest);

    Boolean deleteById(Long id);

    Page<InvestorPropertyResponseForTable> getInvestorPropertyByPagination(PropertiesFilter filter);
}
