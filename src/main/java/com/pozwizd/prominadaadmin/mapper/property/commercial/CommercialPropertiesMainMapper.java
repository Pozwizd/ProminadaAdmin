package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesMainRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesMainResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommercialPropertiesMainMapper {

    @Named("toCommercialPropertiesMainResponse")
    @Mapping(source = "housingState.id", target = "housingStateId")
    CommercialPropertiesMainResponse toResponse(CommercialPropertiesMain commercialPropertiesMain);

    @Named("toCommercialPropertiesMainEntity")
    @Mapping(target = "commercialProperties", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "housingState", source = "housingStateId", qualifiedByName = "findHousingStateById")
    CommercialPropertiesMain toEntity(CommercialPropertiesMainRequest commercialPropertiesMainRequest);

    @Named("partialUpdateCommercialPropertiesMain")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "commercialProperties", ignore = true)
    @Mapping(target = "housingState", source = "housingStateId", qualifiedByName = "findHousingStateById")
    void partialUpdate(CommercialPropertiesMainRequest commercialPropertiesMainRequest,
                       @MappingTarget CommercialPropertiesMain commercialPropertiesMain);
}
