package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.mapper.DocumentFeedbackMapper;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.repository.DocumentFeedbackRepository;
import com.pozwizd.prominadaadmin.service.DocumentFeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentFeedbackServiceImp implements DocumentFeedbackService {

    private final DocumentFeedbackRepository documentFeedbackRepository;
    private final DocumentFeedbackMapper documentFeedbackMapper;


    @Override
    public void deleteById(Long id) {
        documentFeedbackRepository.deleteById(id);
    }

    @Override
    public DocumentFeedback findById(Long id) {
        return documentFeedbackRepository.findById(id).orElse(null);
    }

    @Override
    public DocumentFeedback save(DocumentFeedback documentFeedback) {
        return documentFeedbackRepository.save(documentFeedback);
    }

    @Override
    public DocumentFeedbackResponse getDocumentFeedbackResponseById(Long id) {
        DocumentFeedback documentFeedback = documentFeedbackRepository.findById(id).orElse(null);
        return documentFeedbackMapper.toDocumentFeedbackResponse(documentFeedback);
    }

    @Override
    public List<DocumentFeedback> saveAllDocumentFeedback(List<DocumentFeedback> documentFeedbacks) {
        return documentFeedbackRepository.saveAll(documentFeedbacks);
    }
}
