package com.pozwizd.prominadaadmin.models.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaDtoDrop {
    private Long id;
    private String name;
    private String pathImage;
    private MultipartFile file;
}
