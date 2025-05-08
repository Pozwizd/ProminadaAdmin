package com.pozwizd.prominadaadmin.models.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackRequest {
    private Long id;

    @NotBlank(message = "feedback.validation.name.required")
    private String name;

    @NotBlank(message = "feedback.validation.phone.required")
    private String phoneNumber;

    @NotBlank(message = "feedback.validation.description.required")
    private String description;

}