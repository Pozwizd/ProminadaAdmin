package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandTableResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class,
                ResidentialLandMainMapper.class,
                ResidentialLandGalleryImageMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResidentialLandMapper {

    @Mapping(source = "street.name", target = "street")
    @Mapping(source = "region.name", target = "regDistrictName", defaultExpression = "java(null)")
    @Mapping(source = "district.name", target = "districtName", defaultExpression = "java(null)")
    @Mapping(source = "topozone.name", target = "topozoneName", defaultExpression = "java(null)")
    @Mapping(source = "ownerFullName", target = "ownerFullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "acquisitionDate", target = "acquisitionDate")
    @Mapping(source = "ownershipDoc", target = "ownershipDoc")
    @Mapping(source = "importantComment", target = "importantComment")
    @Mapping(source = "cadastralNumber", target = "cadastralNumber")
    @Mapping(source = "langPurpose", target = "langPurpose")
    @Mapping(source = "adminComment", target = "adminComment")
    @Mapping(source = "residentialLandMain", target = "residentialLandMain")
    @Mapping(source = "dateOfCreating", target = "dateOfCreating")
    @Mapping(target = "hasPhotos", expression = "java(residentialLand.getResidentialLandGalleryImages() != null && !residentialLand.getResidentialLandGalleryImages().isEmpty())")
    ResidentialLandTableResponse toResidentialLandTableResponse(ResidentialLand residentialLand);


    default List<ResidentialLandTableResponse> toResidentialLandTableResponses(List<ResidentialLand> residentialLands) {
        if (residentialLands == null) {
            return Collections.emptyList();
        }
        return residentialLands.stream()
                .map(this::toResidentialLandTableResponse)
                .toList();
    }

    default Page<ResidentialLandTableResponse> toResidentialLandTableResponses(Page<ResidentialLand> residentialLands){
        return residentialLands.map(this::toResidentialLandTableResponse);
    }


    @Mapping(source = "id", target = "id")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "street.id", target = "streetId")
    @Mapping(source = "house.id", target = "houseId")
    @Mapping(source = "topozone.id", target = "topozoneId")
    @Mapping(source = "residentialLandMain", target = "residentialLandMain", qualifiedByName = "toResidentialLandMainResponse")
    @Mapping(source = "realtor.id", target = "realtorId")
    ResidentialLandResponse toResidentialLandResponse(ResidentialLand residentialLand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    void updateResidentialLandFromRequest(ResidentialLandRequest request,
                                          @MappingTarget ResidentialLand residentialLand);


    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    ResidentialLand toResidentialLand(ResidentialLandRequest residentialLandRequest);
}