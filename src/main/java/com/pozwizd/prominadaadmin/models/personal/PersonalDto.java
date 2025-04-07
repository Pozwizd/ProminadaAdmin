package com.pozwizd.prominadaadmin.models.personal;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Personal}
 */
@Data
public class PersonalDto implements Serializable {
    Long id;
    String name;
    String surname;
    // String middleName; // Removed as it's not present in the Personal entity
    String lastName;
    String phoneNumber;
    String email;
    String role;
}