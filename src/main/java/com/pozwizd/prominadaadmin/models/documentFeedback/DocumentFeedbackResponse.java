package com.pozwizd.prominadaadmin.models.documentFeedback;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import lombok.Data;

/**
 * DocumentFeedbackResponse for {@link DocumentFeedback}
 */
@Data
public class DocumentFeedbackResponse {
    private Long id;
    private String name;
    private String pathImage;
}