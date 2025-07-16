package com.pozwizd.prominadaadmin.models.banner;

import com.pozwizd.prominadaadmin.entity.Banner;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * Request for {@link Banner}
 */
@Data
public class BannerRequest implements Serializable {
    Long id;
    @Size(max = 50,min = 5, message = "name.size")
    String name;
    Boolean status;
    List<ImageBannerRequest> imageBannersRequest;
}