package com.pozwizd.prominadaadmin.models.personal;


import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * PersonalRequest for {@link Personal}
 */
@Data
public class PersonalRequest {
    private Long id;

    @NotBlank(message = "Фамилия обязательна")
    private String surname;

    @NotBlank(message = "Имя обязательно")
    private String name;

    @NotBlank(message = "Отчество обязательно")
    private String lastName;

    @NotBlank(message = "Телефон обязателен")
    private String phoneNumber;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;


    private String password;

    private String confirmPassword;

    @NotNull(message = "Роль обязательна")
    private String role;

    @NotNull(message = "Необходимо выбрать хотя бы один филиал")
    private List<Long> branchIds;

    private MultipartFile avatar;

    @Valid
    private List<DocumentFeedbackRequest> documents;

    @Valid
    private List<FeedbackRequest> feedBacks;
}