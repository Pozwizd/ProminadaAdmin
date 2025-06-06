package com.pozwizd.prominadaadmin.models;

import com.pozwizd.prominadaadmin.entity.ContactType;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PhoneNumberResponse {
    private Long id;
    @Pattern(regexp = "\\+380(50|66|95|99|67|68|96|97|98|63|93|73)[0-9]{7}", message = "phone.pattern")
    private String phoneNumber;
    private ContactType contactType;

    public PhoneNumberResponse() {
    }
}
