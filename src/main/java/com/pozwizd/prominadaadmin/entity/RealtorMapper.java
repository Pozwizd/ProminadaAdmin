package com.pozwizd.prominadaadmin.entity;

import com.pozwizd.prominadaadmin.models.personal.RealtorDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RealtorMapper {
    Realtor toEntity(RealtorDto realtorDto);

    RealtorDto toDto(Realtor realtor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Realtor partialUpdate(RealtorDto realtorDto, @MappingTarget Realtor realtor);
}