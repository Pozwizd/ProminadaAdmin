package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialPropertiesFile;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesFileRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesFileResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommercialPropertiesFileMapper {
//    CommercialPropertiesFileResponse toResponse(CommercialPropertiesFile commercialPropertiesMain);
//
//    CommercialPropertiesFile toEntity(CommercialPropertiesFileRequest commercialPropertiesMainRequest);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    CommercialPropertiesFile partialUpdate(CommercialPropertiesFileRequest commercialPropertiesMainRequest,
//                                           CommercialPropertiesFile commercialPropertiesMain);
}
