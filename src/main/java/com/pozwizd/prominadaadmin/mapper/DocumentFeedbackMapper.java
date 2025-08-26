package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.FileServiceImp;
import org.mapstruct.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {FileServiceImp.class},
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentFeedbackMapper {

    @Mapping(target = "pathImage", source = "file", qualifiedByName = "uploadFile")
    DocumentFeedback toEntity(DocumentFeedbackRequest documentFeedbackRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "pathImage", source = "file", qualifiedByName = "uploadFile",
            conditionExpression = "java(documentFeedbackRequest.getFile() != null " +
            "&& !documentFeedbackRequest.getFile().isEmpty()" +
            "&& documentFeedbackRequest.getFile().getSize() > 0)")
    void partialUpdate(DocumentFeedbackRequest documentFeedbackRequest, @MappingTarget DocumentFeedback documentFeedback);

    default <T extends Collection<DocumentFeedback>> void partialUpdate(
            Set<DocumentFeedbackRequest> documentFeedbackRequests,
            T documentFeedbacks) {

        if (documentFeedbackRequests == null) {
            documentFeedbacks.clear();
            return;
        }

        // Собираем ID из запросов
        Set<Long> requestIds = documentFeedbackRequests.stream()
                .map(DocumentFeedbackRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        documentFeedbacks.removeIf(feedback ->
                feedback.getId() != null && !requestIds.contains(feedback.getId()));

        Map<Long, DocumentFeedback> existingFeedbackMap = documentFeedbacks.stream()
                .filter(feedback -> feedback.getId() != null)
                .collect(Collectors.toMap(DocumentFeedback::getId, Function.identity()));

        for (DocumentFeedbackRequest request : documentFeedbackRequests) {
            if (request.getId() != null) {
                DocumentFeedback existingFeedback = existingFeedbackMap.get(request.getId());
                if (existingFeedback != null) {
                    partialUpdate(request, existingFeedback);
                }
            } else {
                DocumentFeedback newFeedback = toEntity(request);
                documentFeedbacks.add(newFeedback);
            }
        }
    }

    DocumentFeedbackResponse toDocumentFeedbackResponse(DocumentFeedback documentFeedback);

}