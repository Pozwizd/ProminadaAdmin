package com.pozwizd.prominadaadmin.mapper.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyMainRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyMainResponse;
import com.pozwizd.prominadaadmin.service.BranchService;
import com.pozwizd.prominadaadmin.service.RealtorService;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class,
                BranchService.class,
                RealtorService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SecondaryPropertyMainMapper {

    @Named("toSecondaryPropertyMainEntity")
    @Mapping(target = "secondaryProperty", ignore = true)
    @Mapping(target = "publicationStatus", source = "publicationStatus")
    @Mapping(target = "housingState", source = "housingStateId", qualifiedByName = "findHousingStateById")
    @Mapping(target = "realtor", source = "employeeCode", qualifiedByName = "getRealtorByCode")
    @Mapping(target = "branch", source = "branchCode", qualifiedByName = "getBranchByCode")
    SecondaryPropertyMain toEntity(SecondaryPropertyMainRequest request);

    @Named("toSecondaryPropertyMainResponse")
    @Mapping(source = "housingState.id", target = "housingStateId")
    SecondaryPropertyMainResponse toResponse(SecondaryPropertyMain entity);

    @Named("partialUpdateSecondaryPropertyMain")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "secondaryProperty", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicationStatus", source = "publicationStatus")
    @Mapping(target = "housingState", source = "housingStateId", qualifiedByName = "findHousingStateById")
    void partialUpdate(
            SecondaryPropertyMainRequest request,
            @MappingTarget SecondaryPropertyMain entity
    );
}
