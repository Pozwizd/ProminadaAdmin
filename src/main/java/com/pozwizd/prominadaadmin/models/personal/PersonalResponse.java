package com.pozwizd.prominadaadmin.models.personal;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackResponse;
import lombok.Data;

import java.util.List;

/**
 * PersonalTableResponse for {@link Personal}
 */
@Data
public class PersonalResponse {
    private Long id;
    private String surname;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String pathAvatar;
    private String role;
    
    private List<FeedbackResponse> feedbackResponses;
    private List<DocumentFeedbackResponse> documentFeedbackResponses;
    private List<Long> idsBranches;
}
