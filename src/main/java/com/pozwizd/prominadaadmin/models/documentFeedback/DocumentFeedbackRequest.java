package com.pozwizd.prominadaadmin.models.documentFeedback;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * DocumentFeedbackResponse for {@link DocumentFeedback}
 */
@Data
public class DocumentFeedbackRequest {
    private Long id;
    private String name;
    private MultipartFile file;
}