package com.pozwizd.prominadaadmin.models.documentFeedback;

import lombok.Data;

@Data
public class DocumentFeedbackResponse {
    private Long id;
    private String comment;
    private String date;
    private String documentName;
    private String documentType;
}