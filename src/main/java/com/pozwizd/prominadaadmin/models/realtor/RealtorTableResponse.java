package com.pozwizd.prominadaadmin.models.realtor;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RealtorTableResponse implements Serializable {
    Long id;
    Long code;
    String fullname;
    String phoneNumber;
    String email;
    String dateOfBirthday;
}
