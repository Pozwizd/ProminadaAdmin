package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderForView;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDtoForTable;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyLayoutDto;
import com.pozwizd.prominadaadmin.models.media.MediaDtoDrop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BuilderPropertyMapper {

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
                .houseSection(entity.getHouseSection())
                .deliveryType(entity.getDeliveryType() != null ? entity.getDeliveryType().getMessageKey() : null)
                .pathToChessPlanFile(entity.getPathToChessPlanFile())
                .pathToMortgageConditionsFile(entity.getPathToMortgageConditionsFile())
                .pathToPriceFile(entity.getPathToPriceFile())
                .cityId(entity.getCity() != null ? entity.getCity().getId().toString() : null)
                .regDistrictId(entity.getRegDistrict() != null ? entity.getRegDistrict().getId().toString() : null)
                .districtId(entity.getDistinct() != null ? entity.getDistinct().getId().toString() : null)
                .topozoneId(entity.getTopozone() != null ? entity.getTopozone().getId().toString() : null)
                .buildingCompanyId(entity.getBuildingCompany() != null ? entity.getBuildingCompany().getId().toString() : null)
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
                .totalFloor(String.valueOf(entity.getTotalFloor()))
                .build();
    }

    @Mapping(target = "deliveryType", ignore = true)
    BuilderProperty toEntityFromRequest(BuilderPropertyDto dto);

    BuilderPropertyGalleryImage toEntityFromRequest(MediaDtoDrop dto);

    MediaDtoDrop toDto(BuilderPropertyGalleryImage gallery);

    BuilderPropertyLayoutDto toDto(BuilderPropertyLayouts entity);

    default BuilderPropertyGalleryImage toEntityFromRequest(MediaDtoDrop dto, BuilderProperty entity) {
        BuilderPropertyGalleryImage gallery = new BuilderPropertyGalleryImage();


        gallery.setId(dto.getId());
        gallery.setName(dto.getFile() != null ? dto.getFile().getOriginalFilename() : null);
        gallery.setPathImage(dto.getPathImage());
        gallery.setBuilderProperty(entity);
        gallery.setSize(dto.getFile() != null
                ? String.valueOf(dto.getFile().getSize())
                : null);
        return gallery;
    }

    default BuilderPropertyLayouts toEntityFromRequest(BuilderPropertyLayoutDto dto, BuilderProperty entity) {
        BuilderPropertyLayouts layout = new BuilderPropertyLayouts();
        layout.setId(dto.getId());
        layout.setName(dto.getName());
        layout.setPriceByM2(dto.getPriceByM2() != null ? dto.getPriceByM2() : 0);
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

    default BuilderForView toDtoForView(BuilderProperty entity) {
        Optional<BuilderPropertyGalleryImage> builderImage = entity.getBuilderPropertyGalleryImages().stream().findFirst();
        return BuilderForView.builder()
                .id(entity.getId())
                .name(entity.getName())
                .district(entity.getDistinct() != null ? entity.getDistinct().getName() : null)
                .topozone(entity.getTopozone() != null ? entity.getTopozone().getName() : null)
                .street(entity.getStreet())
                .roof(String.valueOf(entity.getTotalFloor()))
                .promotion(entity.getIsAction())
                .description(entity.getDescription())
                .pathToImage(builderImage.map(BuilderPropertyGalleryImage::getPathImage).orElse(null))
                .pathToPriceFile(entity.getPathToPriceFile())
                .pathToChessPlanFile(entity.getPathToChessPlanFile())
                .pathToMortgageConditionsFile(entity.getPathToMortgageConditionsFile())
                .company(entity.getBuildingCompany() != null ? entity.getBuildingCompany().getName() : null)
                .build();
    }
}
