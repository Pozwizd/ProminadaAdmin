package com.pozwizd.prominadaadmin.models.realtor;

import com.pozwizd.prominadaadmin.entity.ContactType;
import com.pozwizd.prominadaadmin.entity.PhoneNumber;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link PhoneNumber}
 */
@Data
public class PhoneNumberResponse implements Serializable {
    Long id;
    String phoneNumber;
    ContactType contactType;
}