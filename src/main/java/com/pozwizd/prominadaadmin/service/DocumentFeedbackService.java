package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;

import java.util.List;

public interface DocumentFeedbackService {
    void deleteById(Long id);

    DocumentFeedback findById(Long id);

    DocumentFeedback save(DocumentFeedback documentFeedback);

    DocumentFeedbackResponse getDocumentFeedbackResponseById(Long id);

    List<DocumentFeedback> saveAllDocumentFeedback(List<DocumentFeedback> documentFeedbacks);
}
