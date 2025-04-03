package com.pozwizd.prominadaadmin.models.personal;

import com.pozwizd.prominadaadmin.entity.Realtor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Realtor}
 */
@Data
public class RealtorDto implements Serializable {
    Long id;
    String pathAvatar;
    String name;
    String surname;
    String lastName;
    String email;
}