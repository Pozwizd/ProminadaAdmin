package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.entity.Feedback;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalResponse;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.service.BranchService;
import com.pozwizd.prominadaadmin.service.DocumentFeedbackService;
import com.pozwizd.prominadaadmin.service.FeedbackService;
import com.pozwizd.prominadaadmin.service.FileService;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

default Personal toEntityFromPersonalRequest(Personal oldPersonal,
                                             PersonalRequest personalRequest,
                                             DocumentFeedbackService documentFeedbackService,
                                             BranchService branchService,
                                             FileService fileService,
                                             FeedbackService feedbackService) {
    Personal personal = new Personal();
    personal.setId(oldPersonal.getId());
    personal.setName(personalRequest.getName());
    personal.setSurname(personalRequest.getSurname());
    personal.setLastName(personalRequest.getLastName());
    personal.setPhoneNumber(personalRequest.getPhoneNumber());
    personal.setEmail(personalRequest.getEmail());
    personal.setPassword(personalRequest.getPassword());
    personal.setRole(Role.valueOf(personalRequest.getRole()));

    if (personalRequest.getAvatar() != null) {
        try {
            personal.setPathAvatar(fileService.uploadFile(personalRequest.getAvatar()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } else {
        personal.setPathAvatar(oldPersonal.getPathAvatar());
    }

    List<DocumentFeedback> updatedDocuments = new ArrayList<>();

    if (personalRequest.getDocuments() != null) {
        Map<Long, DocumentFeedbackRequest> requestDocumentsMap = personalRequest.getDocuments().stream()
                .filter(doc -> doc.getId() != null)
                .collect(Collectors.toMap(DocumentFeedbackRequest::getId, doc -> doc));

        if (oldPersonal.getDocumentFeedbacks() != null) {
            for (DocumentFeedback oldDoc : oldPersonal.getDocumentFeedbacks()) {
                if (requestDocumentsMap.containsKey(oldDoc.getId())) {
                    DocumentFeedbackRequest req = requestDocumentsMap.get(oldDoc.getId());

                    if (req.getName() != null) {
                        oldDoc.setName(req.getName());
                    }

                    if (req.getFile() != null) {
                        try {
                            oldDoc.setPath(fileService.uploadFile(req.getFile()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    oldDoc.setPersonal(personal);
                    updatedDocuments.add(oldDoc);
                } else {
                    documentFeedbackService.deleteById(oldDoc.getId());
                }
            }
        }

        // Добавляем новые документы
        for (DocumentFeedbackRequest documentRequest : personalRequest.getDocuments()) {
            if (documentRequest.getId() == null) {
                DocumentFeedback newDocument = new DocumentFeedback();
                newDocument.setName(documentRequest.getName());

                if (documentRequest.getFile() != null) {
                    try {
                        newDocument.setPath(fileService.uploadFile(documentRequest.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                newDocument.setPersonal(personal);
                updatedDocuments.add(newDocument);
            }
        }

        personal.setDocumentFeedbacks(updatedDocuments);
    }

    if (personalRequest.getFeedBacks() != null) {

        List<Feedback> updatedFeedbacks = new ArrayList<>();

        for (FeedbackRequest feedbackRequest : personalRequest.getFeedBacks()) {
            Feedback feedback = new Feedback();
            if (feedbackRequest.getId() != null) {
                feedback.setId(feedbackRequest.getId());
            }
            feedback.setName(feedbackRequest.getName());
            feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
            feedback.setDescription(feedbackRequest.getDescription());
            feedback.setPersonal(personal);
            updatedFeedbacks.add(feedback);
            feedbackService.save(feedback);
        }

        personal.setFeedBacks(updatedFeedbacks);

    }

    personal.setBranches(
            personalRequest.getBranchIds().stream()
                    .map(branchService::getBranchById)
                    .collect(Collectors.toCollection(ArrayList::new))
    );

    return personal;
}

default Personal toEntityFromPersonalRequest(PersonalRequest personalRequest,
                                            DocumentFeedbackService documentFeedbackService,
                                            BranchService branchService,
                                            FileService fileService,
                                            FeedbackService feedbackService) {
    Personal personal = new Personal();

    personal.setName(personalRequest.getName());
    personal.setSurname(personalRequest.getSurname());
    personal.setLastName(personalRequest.getLastName());
    personal.setPhoneNumber(personalRequest.getPhoneNumber());
    personal.setEmail(personalRequest.getEmail());
    personal.setPassword(personalRequest.getPassword());
    personal.setRole(Role.valueOf(personalRequest.getRole()));

    if (personalRequest.getAvatar() != null) {
        try {
            personal.setPathAvatar(fileService.uploadFile(personalRequest.getAvatar()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    if (personalRequest.getDocuments() != null) {
        List<DocumentFeedback> documents = new ArrayList<>();

        for (DocumentFeedbackRequest documentRequest : personalRequest.getDocuments()) {
            DocumentFeedback document = new DocumentFeedback();
            document.setName(documentRequest.getName());

            if (documentRequest.getFile() != null) {
                try {
                    document.setPath(fileService.uploadFile(documentRequest.getFile()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            document.setPersonal(personal);
            documents.add(document);
        }

        personal.setDocumentFeedbacks(documents);
    }


    if (personalRequest.getFeedBacks() != null) {
        List<Feedback> feedbacks = new ArrayList<>();

        for (FeedbackRequest feedbackRequest : personalRequest.getFeedBacks()) {
            Feedback feedback = new Feedback();
            feedback.setName(feedbackRequest.getName());
            feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
            feedback.setDescription(feedbackRequest.getDescription());
            feedback.setPersonal(personal);
            feedbacks.add(feedback);
            feedbackService.save(feedback);
        }

        personal.setFeedBacks(feedbacks);
    }

    if (personalRequest.getBranchIds() != null) {
        personal.setBranches(
                personalRequest.getBranchIds().stream()
                        .map(branchService::getBranchById)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    return personal;
}



    default Page<PersonalResponse> toPersonalProfileResponse(Page<Personal> personalPage) {
        return personalPage.map(this::toPersonalProfileResponse);
    }
}