package com.pozwizd.prominadaadmin.models.media;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class MediaDtoDrop {
    private Long id;
    private String name;
    private String path;
    private MultipartFile file;
}
