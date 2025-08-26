package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandMainRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandMainResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResidentialLandMainMapper {


    @Named("toResidentialLandMainEntity")
    @Mapping(target = "residentialLand", ignore = true)
    ResidentialLandMain toEntity(ResidentialLandMainRequest request);

    @Named("toResidentialLandMainResponse")
    ResidentialLandMainResponse toResponse(ResidentialLandMain entity);


    @Named("partialUpdateResidentialLandMain")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "residentialLand", ignore = true)
    @Mapping(target = "id", ignore = true)
    void partialUpdate(
            ResidentialLandMainRequest request,
            @MappingTarget ResidentialLandMain entity
    );

}