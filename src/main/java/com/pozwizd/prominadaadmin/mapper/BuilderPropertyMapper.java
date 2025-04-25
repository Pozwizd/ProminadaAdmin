package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.personal.PersonalDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface BuilderPropertyMapper {
//    @Mapping(source = "distinct.name", target = "nameDistinct")
//    @Mapping(source = "topozone.name", target = "nameTopozone")
//    BuilderProperty toResponseForTable(BuilderPropertyDto dto);

    default Page<BuilderPropertyDto> toDto(Page<BuilderProperty> page) {
        return page.map(this::toResponseForTable);
    }

    default BuilderPropertyDto toResponseForTable(BuilderProperty entity) {
        return BuilderPropertyDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .nameDistinct(entity.getDistinct() != null ? entity.getDistinct().getName() : null)
                .nameTopozone(entity.getTopozone() != null ? entity.getTopozone().getName() : null)
                .street(entity.getStreet())
                .totalFloor(entity.getFloorPlanFile())
                .build();

    }
}
