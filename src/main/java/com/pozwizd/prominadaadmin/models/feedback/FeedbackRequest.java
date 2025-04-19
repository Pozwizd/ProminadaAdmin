package com.pozwizd.prominadaadmin.models.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackRequest {
    private Long id;

    @NotBlank(message = "Имя обязательно")
    private String name;

    @NotBlank(message = "Телефон обязателен")
    private String phoneNumber;

    @NotBlank(message = "Описание обязательно")
    private String description;

}