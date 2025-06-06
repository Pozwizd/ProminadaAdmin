package com.pozwizd.prominadaadmin.models.realtor;

import com.pozwizd.prominadaadmin.models.PhoneNumberResponse;
import com.pozwizd.prominadaadmin.models.branch.BranchResponse;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackResponse;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RealtorResponse {
    private Long id;
    private String code;
    private String dateOfBirthday;
    private String surname;
    private String name;
    private String lastName;
    private String email;
    private String pathAvatar;
    private Integer amountCustomers;
    private List<FeedbackResponse> feedBacks;
    private List<DocumentFeedbackResponse> documentFeedbacks;
    private List<BranchResponse> branches;
    private List<PhoneNumberResponse> phoneNumbers;
}
