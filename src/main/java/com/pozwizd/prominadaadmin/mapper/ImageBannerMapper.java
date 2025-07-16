package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.ImageBanner;
import com.pozwizd.prominadaadmin.models.banner.ImageBannerRequest;
import com.pozwizd.prominadaadmin.models.banner.ImageBannerResponse;
import com.pozwizd.prominadaadmin.service.FileService;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageBannerMapper {

    // to Entity

    default ImageBanner toEntity(ImageBannerRequest imageBannerRequest, @Context FileService fileService) {
        if (imageBannerRequest == null) {
            return null;
        }
        ImageBanner.ImageBannerBuilder imageBanner = ImageBanner.builder();
        if (imageBannerRequest.getPathImage() != null) {
            try {
                imageBanner.pathImage(mapImage(imageBannerRequest.getPathImage(), fileService));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (imageBannerRequest.getId() != null) {
            imageBanner.id(imageBannerRequest.getId());
        }
        imageBanner.name(imageBannerRequest.getName());
        imageBanner.priority(imageBannerRequest.getPriority());
        return imageBanner.build();
    }

    default List<ImageBanner> toEntity(List<ImageBannerRequest> imageBannerRequests, @Context FileService fileService) {
        return imageBannerRequests.stream()
                .map(imageBannerRequest -> toEntity(imageBannerRequest, fileService))
                .toList();
    }

    // to Response
    ImageBannerResponse toResponse(ImageBanner imageBanner);

    default List<ImageBannerResponse> toResponse(List<ImageBanner> imageBanners) {
        return imageBanners.stream().map(this::toResponse).toList();
    }


    @Named("processImage")
    default String mapImage(MultipartFile file, @Context FileService fileService) throws IOException {
        if (!file.isEmpty()) {
            return fileService.uploadFile(file);
        }
        return null;
    }
}
