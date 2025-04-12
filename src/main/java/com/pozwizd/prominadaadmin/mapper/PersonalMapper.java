package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalProfileResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {FeedbackMapper.class, DocumentFeedbackMapper.class, BranchMapper.class})
public interface PersonalMapper {
    Personal toEntity(PersonalTableResponse personalTableResponse);

    PersonalTableResponse toPersonalTableResponse(Personal personal);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Personal partialUpdate(PersonalTableResponse personalTableResponse, @MappingTarget Personal personal);

    default Page<PersonalTableResponse> toPersonalTableResponse(Page<Personal> personalPage) {
        return personalPage.map(this::toPersonalTableResponse);
    }

    @Mapping(target = "role", expression = "java(personal.getRole().toString())")
    PersonalProfileResponse toPersonalProfileResponse(Personal personal);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    Personal toEntity(PersonalProfileResponse personalProfileResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    Personal partialUpdate(PersonalProfileResponse personalProfileResponse, @MappingTarget Personal personal);

    default Page<PersonalProfileResponse> toPersonalProfileResponse(Page<Personal> personalPage) {
        return personalPage.map(this::toPersonalProfileResponse);
    }
}