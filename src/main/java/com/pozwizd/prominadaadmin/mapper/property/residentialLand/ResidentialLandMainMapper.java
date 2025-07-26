package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandMainRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandMainResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResidentialLandMainMapper {


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResidentialLandMain partialUpdate(ResidentialLandMainResponse residentialLandMainResponse,
                                      @MappingTarget ResidentialLandMain residentialLandMain);

    @Mapping(target = "residentialLand", ignore = true)
    ResidentialLandMain toEntity(ResidentialLandMainRequest request);

    @Named("toResidentialLandMainResponse")
    ResidentialLandMainResponse toResponse(ResidentialLandMain entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "residentialLand", ignore = true)
    @Mapping(target = "id", ignore = true)
    ResidentialLandMain partialUpdate(
            ResidentialLandMainRequest request,
            @MappingTarget ResidentialLandMain entity
    );

}