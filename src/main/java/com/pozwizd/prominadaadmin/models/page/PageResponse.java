package com.pozwizd.prominadaadmin.models.page;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.pozwizd.prominadaadmin.entity.Page}
 */
@Data
@Builder
public class PageResponse implements Serializable {
    Long id;
    String name;
    String title;
    String description;
}