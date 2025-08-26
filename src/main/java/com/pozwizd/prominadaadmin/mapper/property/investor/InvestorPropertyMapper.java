package com.pozwizd.prominadaadmin.mapper.property.investor;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyFile;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyMain;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import com.pozwizd.prominadaadmin.models.property.investor.request.InvestorPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponseForTable;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.forMapper.EntityLookupService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;


import java.time.LocalDate;
import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FileService.class,
                EntityLookupService.class,
                InvestorPropertyMainMapper.class,
                InvestorPropertyFileMapper.class,
                InvestorPropertyGalleryImageMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InvestorPropertyMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "street.id", target = "streetId")
    @Mapping(source = "house.id", target = "houseId")
    @Mapping(source = "topozone.id", target = "topozoneId")
    @Mapping(source = "investorPropertyMain", target = "investorPropertyMain",
            qualifiedByName = "toInvestorPropertyMainResponse")
    InvestorPropertyResponse toResponse(InvestorProperty investorProperty);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "investorPropertyMain", target = "lastCommunication", qualifiedByName = "extractLastCommunication")
    @Mapping(source = "investorPropertyMain", target = "objectCode", qualifiedByName = "extractObjectCode")
    @Mapping(source = "region.name", target = "region")
    @Mapping(source = "city.name", target = "city")
    @Mapping(source = "district.name", target = "district")
    @Mapping(source = "street.name", target = "street")
    @Mapping(source = "topozone.name", target = "topozone")
    @Mapping(source = "investorPropertyMain", target = "totalRooms", qualifiedByName = "extractRooms")
    @Mapping(source = "investorPropertyMain", target = "floor", qualifiedByName = "extractFloor")
    @Mapping(source = "investorPropertyMain", target = "totalFloor", qualifiedByName = "extractFloors")
    @Mapping(source = "investorPropertyMain", target = "totalArea", qualifiedByName = "extractTotalArea")
    @Mapping(source = "investorPropertyMain", target = "price", qualifiedByName = "extractPrice")
    @Mapping(source = "investorPropertyMain", target = "isAdvertising", qualifiedByName = "extractIsAdvertising")
    @Mapping(source = "investorPropertyGalleryImages", target = "hasPhoto", qualifiedByName = "hasImages")
    InvestorPropertyResponseForTable toResponseForTablePage(InvestorProperty investorProperty);

    default List<InvestorPropertyResponseForTable> toResponseForTablePage(List<InvestorProperty> investorProperties) {
        return investorProperties.stream()
                .map(this::toResponseForTablePage)
                .toList();
    }

    default Page<InvestorPropertyResponseForTable> toResponseForTablePage(Page<InvestorProperty> investorProperties) {
        return investorProperties.map(this::toResponseForTablePage);
    }

    @Named("hasImages")
    default Boolean hasImages(List<InvestorPropertyGalleryImage> images) {
        return images != null && !images.isEmpty();
    }


    @Named("extractLastCommunication")
    default LocalDate extractLastCommunication(InvestorPropertyMain main) {
        return main != null ? main.getLastCommunication() : null;
    }

    @Named("extractObjectCode")
    default String extractObjectCode(InvestorPropertyMain main) {
        return main != null ? main.getObjectCode() : null;
    }

    @Named("extractRooms")
    default String extractRooms(InvestorPropertyMain main) {
        return main != null ? String.valueOf(main.getRooms()) : null;
    }

    @Named("extractFloor")
    default String extractFloor(InvestorPropertyMain main) {
        return main != null ? String.valueOf(main.getFloor()) : null;
    }

    @Named("extractFloors")
    default String extractFloors(InvestorPropertyMain main) {
        return main != null ? String.valueOf(main.getFloors()) : null;
    }

    @Named("extractTotalArea")
    default String extractTotalArea(InvestorPropertyMain main) {
        return main != null ? String.valueOf(main.getTotalArea()) : null;
    }

    @Named("extractPrice")
    default String extractPrice(InvestorPropertyMain main) {
        return main != null && main.getPrice() != null ? main.getPrice().toString() : null;
    }

    @Named("extractIsAdvertising")
    default Boolean extractIsAdvertising(InvestorPropertyMain main) {
        return main != null ? main.getIsAdvertising() : null;
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(target = "investorPropertyMain", source = "investorPropertyMainRequest",
            qualifiedByName = "partialUpdateInvestorPropertyMain")
    @Mapping(target = "investorPropertyFiles", source = "investorPropertyFileRequests",
            qualifiedByName = "toInvestorPropertyFileEntityList")
    @Mapping(target = "investorPropertyGalleryImages", source = "investorPropertyGalleryImageRequests",
            qualifiedByName = "toInvestorPropertyGalleryImageEntityList")
    void updateFromRequest(InvestorPropertyRequest request,
                           @MappingTarget InvestorProperty investorProperty);

    @AfterMapping
    default void wireAfterUpdate(@MappingTarget InvestorProperty investorProperty) {
        // set owning side for one-to-one
        InvestorPropertyMain main = investorProperty.getInvestorPropertyMain();
        if (main != null) {
            main.setInvestorProperty(investorProperty);
        }
        // set owning side for one-to-many children
        List<InvestorPropertyFile> files = investorProperty.getInvestorPropertyFiles();
        if (files != null) {
            for (InvestorPropertyFile f : files) {
                if (f != null) f.setInvestorProperty(investorProperty);
            }
        }
        List<InvestorPropertyGalleryImage> images = investorProperty.getInvestorPropertyGalleryImages();
        if (images != null) {
            for (InvestorPropertyGalleryImage img : images) {
                if (img != null) img.setInvestorProperty(investorProperty);
            }
        }
    }

    @Mapping(target = "region", source = "regionId", qualifiedByName = "findRegionById")
    @Mapping(target = "city", source = "cityId", qualifiedByName = "findCityById")
    @Mapping(target = "district", source = "districtId", qualifiedByName = "findDistrictById")
    @Mapping(target = "street", source = "streetId", qualifiedByName = "findStreetById")
    @Mapping(target = "house", source = "houseId", qualifiedByName = "findHouseById")
    @Mapping(target = "topozone", source = "topozoneId", qualifiedByName = "findTopozoneById")
    @Mapping(source = "investorPropertyMainRequest", target = "investorPropertyMain",
            qualifiedByName = "toInvestorPropertyMainEntity")
    @Mapping(target = "investorPropertyFiles", source = "investorPropertyFileRequests",
            qualifiedByName = "toInvestorPropertyFileEntity")
    @Mapping(target = "investorPropertyGalleryImages", source = "investorPropertyGalleryImageRequests",
            qualifiedByName = "toInvestorPropertyGalleryImageEntity")
    InvestorProperty toEntity(InvestorPropertyRequest investorPropertyRequest);
}