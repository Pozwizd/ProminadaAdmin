package com.pozwizd.prominadaadmin.mapper.property.builderProperty;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyResponseForTable;
import com.pozwizd.prominadaadmin.models.property.builderProperty.request.BuilderPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyResponse;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EntityLookupService.class,
                BuilderPropertyGalleryImageMapper.class,
                BuilderPropertyLayoutsMapper.class,
                FileService.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BuilderPropertyMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "street.id", target = "streetId")
    @Mapping(source = "house.id", target = "houseId")
    @Mapping(source = "topozone.id", target = "topozoneId")
    @Mapping(source = "buildingCompany.id", target = "buildingCompanyId")
    @Mapping(source = "builderPropertyGalleryImages", target = "builderPropertyGalleryImages",
            qualifiedByName = "toBuilderPropertyGalleryImageResponseList")
    @Mapping(source = "builderPropertyLayouts", target = "builderPropertyLayouts",
            qualifiedByName = "toBuilderPropertyLayoutsResponseList")
    BuilderPropertyResponse toResponse(BuilderProperty builderProperty);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "buildingCompany", source = "buildingCompanyId", qualifiedByName = "findBuildingCompanyById")
    @Mapping(target = "builderPropertyGalleryImages", source = "builderPropertyGalleryImageRequests",
            qualifiedByName = "partialUpdateBuilderPropertyGalleryImageList")
    @Mapping(target = "builderPropertyLayouts", source = "builderPropertyLayoutsRequests",
            qualifiedByName = "partialUpdateBuilderPropertyLayoutsList")
    @Mapping(
            source = "pathToChessPlanFile",
            target = "pathToChessPlanFile",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyRequest.getPathToChessPlanFile() != null" +
                            " && !builderPropertyRequest.getPathToChessPlanFile().isEmpty()" +
                            " && builderPropertyRequest.getPathToChessPlanFile().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathToMortgageConditionsFile",
            target = "pathToMortgageConditionsFile",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyRequest.getPathToMortgageConditionsFile() != null" +
                            " && !builderPropertyRequest.getPathToMortgageConditionsFile().isEmpty()" +
                            " && builderPropertyRequest.getPathToMortgageConditionsFile().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathToPriceFile",
            target = "pathToPriceFile",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyRequest.getPathToPriceFile() != null" +
                            " && !builderPropertyRequest.getPathToPriceFile().isEmpty()" +
                            " && builderPropertyRequest.getPathToPriceFile().getSize() > 0" +
                            ")"
    )
    void updateFromRequest(BuilderPropertyRequest builderPropertyRequest,
                           @MappingTarget BuilderProperty builderProperty);

    @AfterMapping
    default void wireAfterUpdate(@MappingTarget BuilderProperty builderProperty) {
        List<BuilderPropertyGalleryImage> galleryImages = builderProperty.getBuilderPropertyGalleryImages();
        if (galleryImages != null) {
            for (BuilderPropertyGalleryImage image : galleryImages) {
                if (image != null) image.setBuilderProperty(builderProperty);
            }
        }
        List<BuilderPropertyLayouts> layouts = builderProperty.getBuilderPropertyLayouts();
        if (layouts != null) {
            for (BuilderPropertyLayouts layout : layouts) {
                if (layout != null) layout.setBuilderProperty(builderProperty);
            }
        }
    }

    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "buildingCompany", source = "buildingCompanyId", qualifiedByName = "findBuildingCompanyById")
    @Mapping(target = "builderPropertyGalleryImages", source = "builderPropertyGalleryImageRequests",
            qualifiedByName = "toBuilderPropertyGalleryImageEntity")
    @Mapping(target = "builderPropertyLayouts", source = "builderPropertyLayoutsRequests",
            qualifiedByName = "toBuilderPropertyLayoutsEntity")
    @Mapping(
            source = "pathToChessPlanFile",
            target = "pathToChessPlanFile",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyRequest.getPathToChessPlanFile() != null" +
                            " && !builderPropertyRequest.getPathToChessPlanFile().isEmpty()" +
                            " && builderPropertyRequest.getPathToChessPlanFile().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathToMortgageConditionsFile",
            target = "pathToMortgageConditionsFile",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyRequest.getPathToMortgageConditionsFile() != null" +
                            " && !builderPropertyRequest.getPathToMortgageConditionsFile().isEmpty()" +
                            " && builderPropertyRequest.getPathToMortgageConditionsFile().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathToPriceFile",
            target = "pathToPriceFile",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyRequest.getPathToPriceFile() != null" +
                            " && !builderPropertyRequest.getPathToPriceFile().isEmpty()" +
                            " && builderPropertyRequest.getPathToPriceFile().getSize() > 0" +
                            ")"
    )
    BuilderProperty toEntity(BuilderPropertyRequest builderPropertyRequest);

    @AfterMapping
    default void wireAfterCreate(@MappingTarget BuilderProperty builderProperty) {
        List<BuilderPropertyGalleryImage> galleryImages = builderProperty.getBuilderPropertyGalleryImages();
        if (galleryImages != null) {
            for (BuilderPropertyGalleryImage image : galleryImages) {
                if (image != null) image.setBuilderProperty(builderProperty);
            }
        }
        List<BuilderPropertyLayouts> layouts = builderProperty.getBuilderPropertyLayouts();
        if (layouts != null) {
            for (BuilderPropertyLayouts layout : layouts) {
                if (layout != null) layout.setBuilderProperty(builderProperty);
            }
        }
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "city.name", target = "city")
    @Mapping(source = "district.name", target = "district")
    @Mapping(source = "street.name", target = "street")
    @Mapping(source = "topozone.name", target = "topozone")
    @Mapping(source = "totalFloor", target = "totalFloor")
    BuilderPropertyResponseForTable toResponseForTable(BuilderProperty builderProperty);

    default Page<BuilderPropertyResponseForTable> toResponseForTablePage(Page<BuilderProperty> builderProperties) {
        return builderProperties.map(this::toResponseForTable);
    }

    default List<BuilderPropertyResponseForTable> toResponseForTableList(List<BuilderProperty> builderProperties) {
        return builderProperties.stream()
                .map(this::toResponseForTable)
                .toList();
    }

}
