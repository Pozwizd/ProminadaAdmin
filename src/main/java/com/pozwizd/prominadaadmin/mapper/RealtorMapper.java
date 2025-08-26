package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.FileServiceImp;
import org.mapstruct.*;

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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "pathAvatar", source = "pathAvatar", qualifiedByName = "uploadFileIfPresent",
            conditionExpression = "java(realtorRequest.getPathAvatar() != null " +
                    "&& !realtorRequest.getPathAvatar().isEmpty()" +
                    "&& realtorRequest.getPathAvatar().getSize() > 0)")
    @Mapping(target = "phoneNumbers", source = "phoneNumbers")
    @Mapping(target = "documentFeedbacks", source = "documentFeedbackRequests")
    void partialUpdate(RealtorRequest realtorRequest, @MappingTarget Realtor realtor);
}
