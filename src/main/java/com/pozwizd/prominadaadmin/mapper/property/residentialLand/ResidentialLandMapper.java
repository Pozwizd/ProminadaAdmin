package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponse;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandResponseForTable;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.table.ResidentialLandTableResponse;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FileService.class,
                EntityLookupService.class,
                ResidentialLandMainMapper.class,
                ResidentialLandFileMapper.class,
                ResidentialLandGalleryImageMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResidentialLandMapper {

    //For DATATABLE
    @Mapping(source = "street.name", target = "street")
    @Mapping(source = "region.name", target = "regDistrictName")
    @Mapping(source = "district.name", target = "districtName")
    @Mapping(source = "topozone.name", target = "topozoneName")
    @Mapping(source = "ownerFullName", target = "ownerFullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "acquisitionDate", target = "acquisitionDate")
    @Mapping(source = "ownershipDoc", target = "ownershipDoc")
    @Mapping(source = "importantComment", target = "importantComment")
    @Mapping(source = "cadastralNumber", target = "cadastralNumber")
    @Mapping(source = "langPurpose", target = "langPurpose")
    @Mapping(source = "adminComment", target = "adminComment")
    @Mapping(source = "residentialLandMain", target = "residentialLandMainTableResponse")
    @Mapping(source = "dateOfCreating", target = "dateOfCreating")
    @Mapping(target = "hasPhotos",
            expression = "java(residentialLand.getResidentialLandGalleryImages() != null" +
                    " && !residentialLand.getResidentialLandGalleryImages().isEmpty())")
    ResidentialLandTableResponse toTableResponse(ResidentialLand residentialLand);


    default List<ResidentialLandTableResponse> toTableResponses(List<ResidentialLand> residentialLands) {
        if (residentialLands == null) {
            return Collections.emptyList();
        }
        return residentialLands.stream()
                .map(this::toTableResponse)
                .toList();
    }

    default Page<ResidentialLandTableResponse> toTableResponses(Page<ResidentialLand> residentialLands){
        return residentialLands.map(this::toTableResponse);
    }
    //========================================================================


    @Mapping(source = "id", target = "id")
    @Mapping(source = "residentialLandMain", target = "lastCommunication", qualifiedByName = "extractLastCommunication")
    @Mapping(source = "residentialLandMain", target = "objectCode", qualifiedByName = "extractObjectCode")
    @Mapping(source = "region.name", target = "region")
    @Mapping(source = "city.name", target = "city")
    @Mapping(source = "district.name", target = "district")
    @Mapping(source = "street.name", target = "street")
    @Mapping(source = "topozone.name", target = "topozone")
    @Mapping(source = "residentialLandMain", target = "totalRooms", qualifiedByName = "extractRooms")
    @Mapping(source = "residentialLandMain", target = "floor", qualifiedByName = "extractFloor")
    @Mapping(source = "residentialLandMain", target = "totalFloor", qualifiedByName = "extractFloors")
    @Mapping(source = "residentialLandMain", target = "totalArea", qualifiedByName = "extractTotalArea")
    @Mapping(source = "residentialLandMain", target = "price", qualifiedByName = "extractPrice")
    @Mapping(source = "residentialLandMain", target = "isAdvertising", qualifiedByName = "extractIsAdvertising")
    @Mapping(source = "residentialLandGalleryImages", target = "hasPhoto", qualifiedByName = "hasImages")
    ResidentialLandResponseForTable toResponseForTablePage(ResidentialLand residentialLand);

    default List<ResidentialLandResponseForTable> toResponseForTablePage(List<ResidentialLand> residentialLands) {
        return residentialLands.stream()
                .map(this::toResponseForTablePage)
                .toList();
    }

    default Page<ResidentialLandResponseForTable> toResponseForTablePage(Page<ResidentialLand> residentialLands) {
        return residentialLands.map(this::toResponseForTablePage);
    }

    @Named("hasImages")
    default Boolean hasImages(List<ResidentialLandGalleryImage> images) {
        return images != null && !images.isEmpty();
    }

    @Named("extractLastCommunication")
    default LocalDate extractLastCommunication(ResidentialLandMain main) {
        return main != null ? main.getLastCommunication() : null;
    }

    @Named("extractObjectCode")
    default String extractObjectCode(ResidentialLandMain main) {
        return main != null ? main.getObjectCode() : null;
    }

    @Named("extractRooms")
    default String extractRooms(ResidentialLandMain main) {
        return main != null ? String.valueOf(main.getRooms()) : null;
    }

    @Named("extractFloor")
    default String extractFloor(ResidentialLandMain main) {
        return main != null ? String.valueOf(main.getFloors()) : null;
    }

    @Named("extractFloors")
    default String extractFloors(ResidentialLandMain main) {
        return main != null ? String.valueOf(main.getFloors()) : null;
    }

    @Named("extractTotalArea")
    default String extractTotalArea(ResidentialLandMain main) {
        return main != null ? String.valueOf(main.getTotalArea()) : null;
    }

    @Named("extractPrice")
    default String extractPrice(ResidentialLandMain main) {
        return main != null && main.getPrice() != null ? main.getPrice().toString() : null;
    }

    @Named("extractIsAdvertising")
    default Boolean extractIsAdvertising(ResidentialLandMain main) {
        return main != null ? main.getIsAdvertising() : null;
    }



    @Mapping(source = "id", target = "id")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "street.id", target = "streetId")
    @Mapping(source = "house.id", target = "houseId")
    @Mapping(source = "topozone.id", target = "topozoneId")
    @Mapping(source = "residentialLandMain", target = "residentialLandMain",
            qualifiedByName = "toResidentialLandMainResponse")
    @Mapping(source = "realtor.id", target = "realtorId")
    ResidentialLandResponse toResponse(ResidentialLand residentialLand);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "residentialLandMain", source = "residentialLandMain",
            qualifiedByName = "partialUpdateResidentialLandMain")
    @Mapping(target = "residentialLandFiles", source = "residentialLandFiles",
            qualifiedByName = "toResidentialLandFileEntityList")
    @Mapping(target = "residentialLandGalleryImages", source = "residentialLandGalleryImages",
            qualifiedByName = "toResidentialLandGalleryImageEntityList")
    void updateFromRequest(ResidentialLandRequest request,
                           @MappingTarget ResidentialLand residentialLand);




    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(source = "residentialLandMain", target = "residentialLandMain",
            qualifiedByName = "toResidentialLandMainEntity")
    @Mapping(target = "residentialLandFiles", source = "residentialLandFiles",
            qualifiedByName = "toResidentialLandFileEntity")
    @Mapping(target = "residentialLandGalleryImages", source = "residentialLandGalleryImages",
            qualifiedByName = "toResidentialLandGalleryImageEntity")
    ResidentialLand toEntity(ResidentialLandRequest residentialLandRequest);
}

