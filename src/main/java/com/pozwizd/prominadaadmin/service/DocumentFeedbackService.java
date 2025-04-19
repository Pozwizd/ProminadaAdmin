package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.mapper.DocumentFeedbackMapper;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.repository.DocumentFeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentFeedbackService {

    private final DocumentFeedbackRepository documentFeedbackRepository;
    private final DocumentFeedbackMapper documentFeedbackMapper;



    public void deleteById(Long id) {
        documentFeedbackRepository.deleteById(id);
    }

    public DocumentFeedback findById(Long id) {
        return documentFeedbackRepository.findById(id).orElse(null);
    }

    public DocumentFeedback save(DocumentFeedback documentFeedback) {
        return documentFeedbackRepository.save(documentFeedback);
    }

    public DocumentFeedbackResponse getDocumentFeedbackResponseById(Long id) {
        DocumentFeedback documentFeedback = documentFeedbackRepository.findById(id).orElse(null);
        return documentFeedbackMapper.toDocumentFeedbackResponse(documentFeedback);
    }


    public List<DocumentFeedback> saveAllDocumentFeedback(List<DocumentFeedback> documentFeedbacks) {
        return documentFeedbackRepository.saveAll(documentFeedbacks);
    }
}
