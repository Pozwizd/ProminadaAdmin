package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesMain;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponseForTable;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class,
                FileService.class,
                CommercialPropertiesMainMapper.class,
                CommercialPropertiesFileMapper.class,
                CommercialPropertiesGalleryImageMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommercialPropertiesMapper {

    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "commercialPropertiesMain", source = "commercialPropertiesMain",
            qualifiedByName = "toCommercialPropertiesMainEntity")
    @Mapping(target = "commercialPropertiesFiles", source = "commercialPropertiesFiles",
            qualifiedByName = "toCommercialPropertiesFileEntityList")
    @Mapping(target = "commercialPropertiesGalleryImages", source = "commercialPropertiesGalleryImages",
            qualifiedByName = "toCommercialPropertiesGalleryImageEntityList")
    CommercialProperties toEntity(CommercialPropertiesRequest commercialPropertiesResponse);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "street.id", target = "streetId")
    @Mapping(source = "house.id", target = "houseId")
    @Mapping(source = "topozone.id", target = "topozoneId")
    @Mapping(source = "commercialPropertiesMain", target = "commercialPropertiesMain",
            qualifiedByName = "toCommercialPropertiesMainResponse")
    @Mapping(source = "commercialPropertiesFiles", target = "commercialPropertiesFiles",
            qualifiedByName = "toCommercialPropertiesFileResponseList")
    @Mapping(source = "commercialPropertiesGalleryImages", target = "commercialPropertiesGalleryImages",
            qualifiedByName = "toCommercialPropertiesGalleryImageResponseList")
    CommercialPropertiesResponse toResponse(CommercialProperties commercialProperties);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "commercialPropertiesMain", source = "commercialPropertiesMain",
            qualifiedByName = "partialUpdateCommercialPropertiesMain")
    @Mapping(target = "commercialPropertiesFiles", source = "commercialPropertiesFiles",
            qualifiedByName = "partialUpdateCommercialPropertiesFileList")
    @Mapping(target = "commercialPropertiesGalleryImages", source = "commercialPropertiesGalleryImages",
            qualifiedByName = "partialUpdateCommercialPropertiesGalleryImageList")
    void partialUpdate(CommercialPropertiesRequest commercialPropertiesResponse,
                                       @MappingTarget CommercialProperties commercialProperties);


    default Page<CommercialPropertiesResponse> toResponsePage(Page<CommercialProperties> commercialPropertiesPage) {
        if (commercialPropertiesPage == null) {
            return null;
        }
        return commercialPropertiesPage.map(this::toResponse);
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "commercialPropertiesMain", target = "lastCommunication", qualifiedByName = "extractLastCommunication")
    @Mapping(source = "commercialPropertiesMain", target = "objectCode", qualifiedByName = "extractObjectCode")
    @Mapping(source = "region.name", target = "region")
    @Mapping(source = "city.name", target = "city")
    @Mapping(source = "district.name", target = "district")
    @Mapping(source = "street.name", target = "street")
    @Mapping(source = "topozone.name", target = "topozone")
    @Mapping(source = "commercialPropertiesMain", target = "totalRooms", qualifiedByName = "extractRooms")
    @Mapping(source = "commercialPropertiesMain", target = "floor", qualifiedByName = "extractFloor")
    @Mapping(source = "commercialPropertiesMain", target = "totalFloor", qualifiedByName = "extractTotalFloor")
    @Mapping(source = "commercialPropertiesMain", target = "totalArea", qualifiedByName = "extractTotalArea")
    @Mapping(source = "commercialPropertiesMain", target = "price", qualifiedByName = "extractPrice")
    @Mapping(source = "commercialPropertiesMain", target = "isAdvertising", qualifiedByName = "extractIsAdvertising")
    @Mapping(source = "commercialPropertiesGalleryImages", target = "hasPhoto", qualifiedByName = "hasImages")
    CommercialPropertiesResponseForTable toResponseForTablePage(CommercialProperties commercialProperties);

    default List<CommercialPropertiesResponseForTable> toResponseForTablePage(List<CommercialProperties> commercialProperties) {
        return commercialProperties.stream()
                .map(this::toResponseForTablePage)
                .toList();
    }

    default Page<CommercialPropertiesResponseForTable> toResponseForTablePage(Page<CommercialProperties> commercialProperties) {
        return commercialProperties.map(this::toResponseForTablePage);
    }

    @Named("hasImages")
    default Boolean hasImages(List<CommercialPropertiesGalleryImage> images) {
        return images != null && !images.isEmpty();
    }

    @Named("extractLastCommunication")
    default LocalDate extractLastCommunication(CommercialPropertiesMain main) {
        return main != null ? main.getLastCommunication() : null;
    }

    @Named("extractObjectCode")
    default String extractObjectCode(CommercialPropertiesMain main) {
        return main != null ? main.getObjectCode() : null;
    }

    @Named("extractRooms")
    default String extractRooms(CommercialPropertiesMain main) {
        return main != null ? String.valueOf(main.getRoomCount()) : null;
    }

    @Named("extractFloor")
    default String extractFloor(CommercialPropertiesMain main) {
        return main != null ? String.valueOf(main.getFloor()) : null;
    }

    @Named("extractTotalFloor")
    default String extractTotalFloor(CommercialPropertiesMain main) {
        return main != null ? String.valueOf(main.getTotalFloor()) : null;
    }

    @Named("extractTotalArea")
    default String extractTotalArea(CommercialPropertiesMain main) {
        return main != null ? String.valueOf(main.getArea()) : null;
    }

    @Named("extractPrice")
    default String extractPrice(CommercialPropertiesMain main) {
        return main != null && main.getPrice() != null ? main.getPrice().toString() : null;
    }

    @Named("extractIsAdvertising")
    default Boolean extractIsAdvertising(CommercialPropertiesMain main) {
        return main != null ? main.getIsAdvertising() : null;
    }



}