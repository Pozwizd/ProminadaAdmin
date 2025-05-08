package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentFeedbackMapper {
    DocumentFeedback toEntity(DocumentFeedbackResponse documentFeedbackResponse);

    DocumentFeedbackResponse toDocumentFeedbackResponse(DocumentFeedback documentFeedback);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentFeedback partialUpdate(DocumentFeedbackResponse documentFeedbackResponse, @MappingTarget DocumentFeedback documentFeedback);

    default Page<DocumentFeedbackResponse> toDocumentFeedbackResponse(Page<DocumentFeedback> documentFeedbackPage) {
        return documentFeedbackPage.map(this::toDocumentFeedbackResponse);
    }

}