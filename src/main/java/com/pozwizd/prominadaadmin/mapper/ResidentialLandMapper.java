package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandTableResponse;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring",
        uses = {EntityLookupService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResidentialLandMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "houseNumber", target = "houseNumber")
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
            return java.util.Collections.emptyList();
        }
        return residentialLands.stream()
                .map(this::toResidentialLandTableResponse)
                .toList();
    }


    ResidentialLandResponse toResidentialLandResponse(ResidentialLand residentialLand);



    default Page<ResidentialLandTableResponse> toResidentialLandTableResponses(Page<ResidentialLand> residentialLands){
        return residentialLands.map(this::toResidentialLandTableResponse);
    };


    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    void updateResidentialLandFromRequest(ResidentialLandRequest request,
                                          @MappingTarget ResidentialLand residentialLand);
}