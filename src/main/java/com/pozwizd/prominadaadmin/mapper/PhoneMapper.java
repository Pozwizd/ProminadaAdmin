package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.PhoneNumber;
import com.pozwizd.prominadaadmin.models.PhoneNumberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PhoneMapper {
    @Mapping(target = "contactType", ignore = true)
    PhoneNumberResponse toDtoPhone(PhoneNumber phoneNumber);
}
