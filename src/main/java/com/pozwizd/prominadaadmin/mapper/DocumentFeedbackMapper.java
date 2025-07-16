package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.FileServiceImp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {FileServiceImp.class},
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentFeedbackMapper {

    @Mapping(target = "pathImage", source = "file", qualifiedByName = "uploadFile")
    DocumentFeedback toEntity(DocumentFeedbackRequest documentFeedbackRequest);

    DocumentFeedbackResponse toDocumentFeedbackResponse(DocumentFeedback documentFeedback);

}