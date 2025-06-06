package com.pozwizd.prominadaadmin.models.realtor;

import com.pozwizd.prominadaadmin.models.PhoneNumberResponse;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import com.pozwizd.prominadaadmin.validation.PasswordMatch;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@PasswordMatch(message = "password.match")
public class RealtorRequest {
    private Long id;

    @NotBlank(message = "code.required")
    private String code;

    @NotBlank(message = "surname.required")
    private String surname;

    @NotBlank(message = "name.required")
    private String name;

    @NotBlank(message = "lastname.required")
    private String lastName;

    @NotBlank(message = "date.required")
    private String dateOfBirthday;

    @NotBlank(message = "email.required")
    @Email(message = "email.format")
    private String email;

    private String password;

    private String confirmPassword;

    @NotNull(message = "branches.required")
    private List<Long> branchIds;

    private MultipartFile avatar;

    @Valid
    private List<DocumentFeedbackRequest> documents;

    @Valid
    private List<FeedbackRequest> feedBacks;

    @Valid
    private List<PhoneNumberResponse> phoneNumbers;
}
