package com.pozwizd.prominadaadmin.models.realtor;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Realtor}
 */
@Data
public class RealtorRequest implements Serializable {
    Long id;
    Long code;
    MultipartFile pathAvatar;
    String name;
    String surname;
    String lastName;
    String email;
    LocalDate birthday;
    String password;
    String confirmPassword;
    List<PhoneNumberResponse> phoneNumbers;
    List<DocumentFeedbackRequest> documentFeedbackRequests;
}