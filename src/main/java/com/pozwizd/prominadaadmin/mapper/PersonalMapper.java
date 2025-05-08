package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
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
    PersonalResponse toPersonalProfileResponse(Personal personal);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    Personal toEntity(PersonalResponse personalResponse);

    default Personal toUpdateEntityFromPersonalRequest(Personal oldPersonal,
                                                       PersonalRequest personalRequest) {
        Personal personal = new Personal();
        personal.setId(oldPersonal.getId());
        personal.setName(personalRequest.getName());
        personal.setSurname(personalRequest.getSurname());
        personal.setLastName(personalRequest.getLastName());
        personal.setPhoneNumber(personalRequest.getPhoneNumber());
        personal.setEmail(personalRequest.getEmail());
        personal.setPassword(personalRequest.getPassword());
        personal.setRole(Role.valueOf(personalRequest.getRole()));

        return personal;
    }

    default Personal toEntityFromPersonalRequest(PersonalRequest personalRequest) {
        Personal personal = new Personal();

        personal.setName(personalRequest.getName());
        personal.setSurname(personalRequest.getSurname());
        personal.setLastName(personalRequest.getLastName());
        personal.setPhoneNumber(personalRequest.getPhoneNumber());
        personal.setEmail(personalRequest.getEmail());
        personal.setRole(Role.valueOf(personalRequest.getRole()));
        return personal;
    }


    default Page<PersonalResponse> toPersonalProfileResponse(Page<Personal> personalPage) {
        return personalPage.map(this::toPersonalProfileResponse);
    }
}