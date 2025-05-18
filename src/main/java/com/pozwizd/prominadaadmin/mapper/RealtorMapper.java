package com.pozwizd.prominadaadmin.mapper;


import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorResponse;
import com.pozwizd.prominadaadmin.models.realtor.RealtorTableResponse;
import com.pozwizd.prominadaadmin.util.DateUtil;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RealtorMapper {

    Realtor toEntity(RealtorTableResponse realtorTableResponse);


    default RealtorTableResponse toRealtorTableResponse(Realtor realtor) {
        return RealtorTableResponse
                .builder()
                .id(realtor.getId())
                .code(Long.parseLong(realtor.getCode()))
                .fullname(realtor.getLastName() + " " + realtor.getName() + " " + realtor.getSurname())
                .email(realtor.getEmail())
                .dateOfBirthday(DateUtil.toFormatDateFromDB(realtor.getDateOfBirthday(),"dd.MM.yyyy"))
                .build();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Realtor partialUpdate(RealtorTableResponse realtorTableResponse, @MappingTarget Realtor realtor);

    default Page<RealtorTableResponse> toRealtorTableResponse(Page<Realtor> realtorPage) {
        return realtorPage.map(this::toRealtorTableResponse);
    }

    //    @Mapping(target = "role", expression = "java(realtor.getRole().toString())")
    RealtorResponse toRealtorProfileResponse(Realtor realtor);

    //    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    Realtor toEntity(RealtorResponse realtorResponse);

    default Realtor toUpdateEntityFromRealtorRequest(Realtor oldRealtor,
                                                     PersonalRequest realtorRequest) {
        Realtor realtor = new Realtor();
        realtor.setId(oldRealtor.getId());
        realtor.setName(realtorRequest.getName());
        realtor.setSurname(realtorRequest.getSurname());
        realtor.setLastName(realtorRequest.getLastName());
        realtor.setEmail(realtorRequest.getEmail());
        realtor.setPassword(realtorRequest.getPassword());

        return realtor;
    }

    default Realtor toEntityFromRealtorRequest(RealtorRequest realtorRequest) {
        Realtor realtor = new Realtor();
        realtor.setName(realtorRequest.getName());
        realtor.setSurname(realtorRequest.getSurname());
        realtor.setLastName(realtorRequest.getLastName());
        realtor.setEmail(realtorRequest.getEmail());
        return realtor;
    }


    default Page<RealtorResponse> toRealtorProfileResponse(Page<Realtor> realtorPage) {
        return realtorPage.map(this::toRealtorProfileResponse);
    }
}