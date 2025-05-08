package com.pozwizd.prominadaadmin.models.personal;

import com.pozwizd.prominadaadmin.entity.Personal;
import lombok.Data;

import java.io.Serializable;

/**
 * PersonalTableResponse for {@link Personal}
 */
@Data
public class PersonalTableResponse implements Serializable {
    Long id;
    String name;
    String surname;
    String lastName;
    String phoneNumber;
    String email;
    String role;
}