package com.pozwizd.prominadaadmin.models.personal;


import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import com.pozwizd.prominadaadmin.validation.PasswordMatch;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * PersonalRequest for {@link Personal}
 */
@Data
@PasswordMatch(message = "password.match")
public class PersonalRequest {
    private Long id;

    @NotBlank(message = "surname.required")
    private String surname;

    @NotBlank(message = "name.required")
    private String name;

    @NotBlank(message = "lastname.required")
    private String lastName;

    @Pattern(regexp = "\\+380(50|66|95|99|67|68|96|97|98|63|93|73)[0-9]{7}", message = "phone.pattern")
    private String phoneNumber;

    @NotBlank(message = "email.required")
    @Email(message = "email.format")
    private String email;


    private String password;

    private String confirmPassword;

    @NotNull(message = "role.required")
    private String role;

    @NotNull(message = "branches.required")
    private List<Long> branchIds;

    private MultipartFile avatar;

    @Valid
    private List<DocumentFeedbackRequest> documents;

    @Valid
    private List<FeedbackRequest> feedBacks;
}