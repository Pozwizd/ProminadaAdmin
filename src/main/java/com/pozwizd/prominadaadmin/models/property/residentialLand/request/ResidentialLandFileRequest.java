package com.pozwizd.prominadaadmin.models.property.residentialLand.request;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link ResidentialLandFile}
 */
@Data
public class ResidentialLandFileRequest implements Serializable {
    Long id;
    String name;
    MultipartFile file;
}