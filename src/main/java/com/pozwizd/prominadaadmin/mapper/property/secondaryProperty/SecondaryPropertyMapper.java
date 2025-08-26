package com.pozwizd.prominadaadmin.mapper.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyMain;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponseForTable;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponseForTable;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FileService.class,
                EntityLookupService.class,
                SecondaryPropertyMainMapper.class,
                SecondaryPropertyFileMapper.class,
                SecondaryPropertyGalleryImageMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SecondaryPropertyMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "topozone.id", target = "topozoneId")
    @Mapping(source = "street.id", target = "streetId")
    @Mapping(source = "house.id", target = "houseId")
    @Mapping(source = "houseSection", target = "houseSection")
    @Mapping(source = "flatNumber", target = "flatNumber")
    @Mapping(source = "secondaryPropertyMain", target = "secondaryPropertyMain",
            qualifiedByName = "toSecondaryPropertyMainResponse")
    SecondaryPropertyResponse toResponse(SecondaryProperty secondaryProperty);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "secondaryPropertyMain", target = "lastCommunication", qualifiedByName = "extractLastCommunication")
    @Mapping(source = "secondaryPropertyMain", target = "objectCode", qualifiedByName = "extractObjectCode")
    @Mapping(source = "region.name", target = "region")
    @Mapping(source = "city.name", target = "city")
    @Mapping(source = "district.name", target = "district")
    @Mapping(source = "street.name", target = "street")
    @Mapping(source = "topozone.name", target = "topozone")
    @Mapping(source = "secondaryPropertyMain", target = "totalRooms", qualifiedByName = "extractRooms")
    @Mapping(source = "secondaryPropertyMain", target = "floor", qualifiedByName = "extractFloor")
    @Mapping(source = "secondaryPropertyMain", target = "totalFloor", qualifiedByName = "extractFloors")
    @Mapping(source = "secondaryPropertyMain", target = "totalArea", qualifiedByName = "extractTotalArea")
    @Mapping(source = "secondaryPropertyMain", target = "price", qualifiedByName = "extractPrice")
    @Mapping(source = "secondaryPropertyMain", target = "isAdvertising", qualifiedByName = "extractIsAdvertising")
    @Mapping(source = "secondaryPropertyGalleryImages", target = "hasPhoto", qualifiedByName = "hasImages")
    SecondaryPropertyResponseForTable toResponseForTablePage(SecondaryProperty secondaryProperty);

    default List<SecondaryPropertyResponseForTable> toResponseForTablePage(List<SecondaryProperty> secondaryProperties) {
        return secondaryProperties.stream()
                .map(this::toResponseForTablePage)
                .toList();
    }

    default Page<SecondaryPropertyResponseForTable> toResponseForTablePage(Page<SecondaryProperty> secondaryProperties) {
        return secondaryProperties.map(this::toResponseForTablePage);
    }

    @Named("hasImages")
    default Boolean hasImages(List<SecondaryPropertyGalleryImage> images) {
        return images != null && !images.isEmpty();
    }

    @Named("extractLastCommunication")
    default LocalDate extractLastCommunication(SecondaryPropertyMain main) {
        return main != null ? main.getLastCommunication() : null;
    }

    @Named("extractObjectCode")
    default String extractObjectCode(SecondaryPropertyMain main) {
        return main != null ? main.getObjectCode() : null;
    }

    @Named("extractRooms")
    default String extractRooms(SecondaryPropertyMain main) {
        return main != null ? String.valueOf(main.getRooms()) : null;
    }

    @Named("extractFloor")
    default String extractFloor(SecondaryPropertyMain main) {
        return main != null ? String.valueOf(main.getFloor()) : null;
    }

    @Named("extractFloors")
    default String extractFloors(SecondaryPropertyMain main) {
        return main != null ? String.valueOf(main.getFloors()) : null;
    }

    @Named("extractTotalArea")
    default String extractTotalArea(SecondaryPropertyMain main) {
        return main != null ? String.valueOf(main.getTotalArea()) : null;
    }

    @Named("extractPrice")
    default String extractPrice(SecondaryPropertyMain main) {
        return main != null && main.getPrice() != null ? main.getPrice().toString() : null;
    }

    @Named("extractIsAdvertising")
    default Boolean extractIsAdvertising(SecondaryPropertyMain main) {
        return main != null ? main.getIsAdvertising() : null;
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "secondaryPropertyMain", source = "secondaryPropertyMainRequest",
            qualifiedByName = "partialUpdateSecondaryPropertyMain")
    @Mapping(target = "secondaryPropertyFiles", source = "secondaryPropertyFileRequests",
            qualifiedByName = "partialUpdateSecondaryPropertyFileList")
    @Mapping(target = "secondaryPropertyGalleryImages", source = "secondaryPropertyGalleryImageRequests",
            qualifiedByName = "toSecondaryPropertyGalleryImageEntityList")
    void updateSecondaryPropertyFromRequest(SecondaryPropertyRequest request,
                                            @MappingTarget SecondaryProperty secondaryProperty);

    @AfterMapping
    default void wireAfterUpdate(@MappingTarget SecondaryProperty secondaryProperty) {
        SecondaryPropertyMain main = secondaryProperty.getSecondaryPropertyMain();
        if (main != null) {
            main.setSecondaryProperty(secondaryProperty);
        }
        List<SecondaryPropertyFile> files = secondaryProperty.getSecondaryPropertyFiles();
        if (files != null) {
            for (SecondaryPropertyFile f : files) {
                if (f != null) f.setSecondaryProperty(secondaryProperty);
            }
        }
        List<SecondaryPropertyGalleryImage> images = secondaryProperty.getSecondaryPropertyGalleryImages();
        if (images != null) {
            for (SecondaryPropertyGalleryImage img : images) {
                if (img != null) img.setSecondaryProperty(secondaryProperty);
            }
        }
    }

    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(source = "secondaryPropertyMainRequest", target = "secondaryPropertyMain",
            qualifiedByName = "toSecondaryPropertyMainEntity")
    @Mapping(target = "secondaryPropertyFiles", source = "secondaryPropertyFileRequests",
            qualifiedByName = "toSecondaryPropertyFileEntity")
    @Mapping(target = "secondaryPropertyGalleryImages", source = "secondaryPropertyGalleryImageRequests",
            qualifiedByName = "toSecondaryPropertyGalleryImageEntity")
    SecondaryProperty toEntity(SecondaryPropertyRequest secondaryPropertyRequest);

    @AfterMapping
    default void wireAfterCreate(@MappingTarget SecondaryProperty secondaryProperty) {
        SecondaryPropertyMain main = secondaryProperty.getSecondaryPropertyMain();
        if (main != null) {
            main.setSecondaryProperty(secondaryProperty);
        }
        List<SecondaryPropertyFile> files = secondaryProperty.getSecondaryPropertyFiles();
        if (files != null) {
            for (SecondaryPropertyFile f : files) {
                if (f != null) f.setSecondaryProperty(secondaryProperty);
            }
        }
        List<SecondaryPropertyGalleryImage> images = secondaryProperty.getSecondaryPropertyGalleryImages();
        if (images != null) {
            for (SecondaryPropertyGalleryImage img : images) {
                if (img != null) img.setSecondaryProperty(secondaryProperty);
            }
        }
    }
}
