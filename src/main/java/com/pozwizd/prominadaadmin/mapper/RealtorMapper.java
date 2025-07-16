package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.FileServiceImp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {FileServiceImp.class, DocumentFeedbackMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface RealtorMapper {

    @Mapping(target = "pathAvatar", source = "pathAvatar", qualifiedByName = "uploadFile")
    @Mapping(target = "phoneNumbers", source = "phoneNumbers")
    @Mapping(target = "documentFeedbacks", source = "documentFeedbackRequests")
    Realtor toEntity(RealtorRequest realtorRequest);


    @Mapping(target = "documentFeedbackResponses", source = "documentFeedbacks")
    RealtorResponse toRealtorResponse(Realtor realtor);
}
