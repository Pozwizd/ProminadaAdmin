package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.personal.PersonalDto;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonalMapper {
    Personal toEntity(PersonalDto personalDto);

    PersonalDto toDto(Personal personal);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Personal partialUpdate(PersonalDto personalDto, @MappingTarget Personal personal);

    default Page<PersonalDto> toDto(Page<Personal> personalPage) {
        return personalPage.map(this::toDto);
    }
}