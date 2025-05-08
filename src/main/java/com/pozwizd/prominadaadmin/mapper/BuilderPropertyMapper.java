package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDtoForTable;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyLayoutDto;
import com.pozwizd.prominadaadmin.models.media.MediaDtoDrop;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BuilderPropertyMapper {
//    @Mapping(source = "distinct.name", target = "nameDistinct")
//    @Mapping(source = "topozone.name", target = "nameTopozone")
//    BuilderProperty toResponseForTable(BuilderPropertyDto dto);

    default Page<BuilderPropertyDtoForTable> toDto(Page<BuilderProperty> page) {
        return page.map(this::toResponseForTable);
    }

    default BuilderPropertyDto toDto(BuilderProperty entity) {
        List<MediaDtoDrop> medias = entity.getBuilderPropertyGalleryImages().stream().map(this::toDto).toList();
        List<BuilderPropertyLayoutDto> layouts = entity.getBuilderPropertyLayouts().stream().map(this::toDto).toList();
        return BuilderPropertyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .street(entity.getStreet())
                .totalFloor(entity.getTotalFloor())
                .pathToChessPlanFile(entity.getPathToChessPlanFile())
                .pathToMortgageConditionsFile(entity.getPathToMortgageConditionsFile())
                .pathToPriceFile(entity.getPathToPriceFile())
                .cityId(entity.getCity() != null ? entity.getCity().getId().toString() : null)
                .regDistrictId(entity.getRegDistrict() != null ? entity.getRegDistrict().getId().toString() : null)
                .districtId(entity.getDistinct() != null ? entity.getDistinct().getId().toString() : null)
                .topozoneId(entity.getTopozone() != null ? entity.getTopozone().getId().toString() : null)
                .buildingCompanyId(entity.getBuildingCompany() != null ? entity.getBuildingCompany().getId().toString() : null)
                .deliveryDateId(entity.getDeliveryDate()!=null?entity.getDeliveryDate().toString():null)
                .houseNumber(entity.getHouseNumber())
                .phoneNumber(entity.getPhoneNumber())
                .description(entity.getDescription())
                .actionDescription(entity.getActionDescription())
                .actionTitle(entity.getActionTitle())
                .isAction(entity.getIsAction())
                .filesDto(medias)
                .layoutDto(layouts)
                .build();
    }

    default BuilderPropertyDtoForTable toResponseForTable(BuilderProperty entity) {
        return BuilderPropertyDtoForTable
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .nameDistinct(entity.getDistinct() != null ? entity.getDistinct().getName() : null)
                .nameTopozone(entity.getTopozone() != null ? entity.getTopozone().getName() : null)
                .street(entity.getStreet())
                .totalFloor(entity.getPathToChessPlanFile())
                .build();
    }

    BuilderProperty toEntityFromRequest(BuilderPropertyDto dto);

    BuilderPropertyGalleryImage toEntityFromRequest(MediaDtoDrop dto);

    MediaDtoDrop toDto(BuilderPropertyGalleryImage gallery);

    BuilderPropertyLayoutDto toDto(BuilderPropertyLayouts entity);
    default BuilderPropertyGalleryImage toEntityFromRequest(MediaDtoDrop dto, BuilderProperty entity) {
        BuilderPropertyGalleryImage gallery = new BuilderPropertyGalleryImage();
        gallery.setId(dto.getId());
        gallery.setName(dto.getName());
        gallery.setPathImage(dto.getPathImage());
        gallery.setBuilderProperty(entity);
        return gallery;
    }

    default BuilderPropertyLayouts toEntityFromRequest(BuilderPropertyLayoutDto dto, BuilderProperty entity) {
        BuilderPropertyLayouts layout = new BuilderPropertyLayouts();
        layout.setId(dto.getId());
        layout.setName(dto.getName());
        layout.setPriceByM2(dto.getPriceByM2());
        layout.setRooms(dto.getRooms());
        layout.setTotalArea(dto.getTotalArea());
        layout.setLivingArea(dto.getLivingArea());
        layout.setKitchenArea(dto.getKitchenArea());
        layout.setVisibleForSite(dto.getVisibleForSite());
        layout.setNameFile1(dto.getFileName1());
        layout.setNameFile2(dto.getFileName2());
        layout.setNameFile3(dto.getFileName3());
        layout.setPathImage1(dto.getPathImage1());
        layout.setPathImage2(dto.getPathImage2());
        layout.setPathImage3(dto.getPathImage3());
        layout.setDescription(dto.getDescription());
        layout.setBuilderProperty(entity);
        return layout;
    }
}
