package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Feedback;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackResponse;
import org.mapstruct.*;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {
    Feedback toEntity(FeedbackResponse feedbackResponse);

    @Mapping(source = "description", target = "text")
    FeedbackResponse toFeedbackResponse(Feedback feedback);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "text", target = "description")
    Feedback partialUpdate(FeedbackResponse feedbackResponse, @MappingTarget Feedback feedback);

    default Page<FeedbackResponse> toFeedbackResponse(Page<Feedback> feedbackPage) {
        return feedbackPage.map(this::toFeedbackResponse);
    }
}