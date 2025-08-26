package com.pozwizd.prominadaadmin.mapper.property.investor;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyMain;
import com.pozwizd.prominadaadmin.models.property.investor.request.InvestorPropertyMainRequest;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyMainResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InvestorPropertyMainMapper {


    @Named("toInvestorPropertyMainEntity")
    @Mapping(target = "investorProperty", ignore = true)
    @Mapping(source = "housingStateId", target = "housingState", qualifiedByName = "findHousingStateById")
    InvestorPropertyMain toEntity(InvestorPropertyMainRequest request);

    @Named("toInvestorPropertyMainResponse")
    @Mapping(source = "housingState.id", target = "housingStateId")
    InvestorPropertyMainResponse toResponse(InvestorPropertyMain entity);

    @Named("partialUpdateInvestorPropertyMain")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "investorProperty", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "housingStateId", target = "housingState", qualifiedByName = "findHousingStateById")
    void partialUpdate(
            InvestorPropertyMainRequest request,
            @MappingTarget InvestorPropertyMain entity
    );

}