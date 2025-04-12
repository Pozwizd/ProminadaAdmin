package com.pozwizd.prominadaadmin.models.feedback;

import com.pozwizd.prominadaadmin.entity.Feedback;
import lombok.Data;
/**
 * DTO for {@link Feedback}
 */
@Data
public class FeedbackResponse {
    
    private Long id;
    private String name;
    private String phoneNumber;
    private String text;
    
}