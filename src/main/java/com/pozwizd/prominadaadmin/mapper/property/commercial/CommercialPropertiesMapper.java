package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.mapper.property.residentialLand.ResidentialLandGalleryImageMapper;
import com.pozwizd.prominadaadmin.mapper.property.residentialLand.ResidentialLandMainMapper;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommercialPropertiesMapper {

    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "commercialPropertiesFiles", ignore = true)
    @Mapping(target = "commercialPropertiesGalleryImages", ignore = true)
    CommercialProperties toEntity(CommercialPropertiesRequest commercialPropertiesResponse);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "street.id", target = "streetId")
    @Mapping(source = "house.id", target = "houseId")
    @Mapping(source = "topozone.id", target = "topozoneId")
    CommercialPropertiesResponse toResponse(CommercialProperties commercialProperties);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "commercialPropertiesFiles", ignore = true)
    @Mapping(target = "commercialPropertiesGalleryImages", ignore = true)
    CommercialProperties partialUpdate(CommercialPropertiesRequest commercialPropertiesResponse,
                                       @MappingTarget CommercialProperties commercialProperties);


    default Page<CommercialPropertiesResponse> toResponsePage(Page<CommercialProperties> commercialPropertiesPage) {
        if (commercialPropertiesPage == null) {
            return null;
        }

        return commercialPropertiesPage.map(this::toResponse);
    }

}