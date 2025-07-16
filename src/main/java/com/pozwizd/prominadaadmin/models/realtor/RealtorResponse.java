package com.pozwizd.prominadaadmin.models.realtor;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Response for {@link Realtor}
 */
@Data
public class RealtorResponse implements Serializable {
    Long id;
    Long code;
    String pathAvatar;
    String name;
    String surname;
    String lastName;
    String email;
    LocalDate birthday;
    List<PhoneNumberResponse> phoneNumbers;
    List<DocumentFeedbackResponse> documentFeedbackResponses;
}