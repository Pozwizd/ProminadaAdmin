package com.pozwizd.prominadaadmin.mapper;


import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RealtorMapper {
//    Realtor toEntity(RealtorDtoTable realtorDtoTable);
//
//    RealtorDtoTable toDto(Realtor realtor);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Realtor partialUpdate(RealtorDtoTable realtorDtoTable, @MappingTarget Realtor realtor);
//
//    default Page<RealtorDtoTable> toDto(Page<Realtor> realtorPage) {
//        return realtorPage.map(this::toDto);
//    }
}