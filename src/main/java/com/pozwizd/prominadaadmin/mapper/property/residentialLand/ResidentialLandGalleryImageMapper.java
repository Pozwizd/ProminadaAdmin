package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandGalleryImageResponse;
import com.pozwizd.prominadaadmin.service.FileService;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FileService.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ResidentialLandGalleryImageMapper {

    @Named("toResidentialLandGalleryImageEntity")
    @Mapping(source = "pathImage", target = "pathImage", qualifiedByName = "uploadFile")
    @Mapping(target = "residentialLand", ignore = true)
    ResidentialLandGalleryImage toEntity(ResidentialLandGalleryImageRequest residentialLandGalleryImageRequest);

    @Named("partialUpdateResidentialLandGalleryImage")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "residentialLand", ignore = true)
    @Mapping(
            source = "pathImage", target = "pathImage", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "residentialLandGalleryImageRequest.getPathImage() != null" +
                            " && !residentialLandGalleryImageRequest.getPathImage().isEmpty()" +
                            " && residentialLandGalleryImageRequest.getPathImage().getSize() > 0" +
                            ")"
    )
    void partialUpdate(ResidentialLandGalleryImageRequest residentialLandGalleryImageRequest,
                       @MappingTarget ResidentialLandGalleryImage residentialLandGalleryImage);

    @Named("toResidentialLandGalleryImageResponse")
    ResidentialLandGalleryImageResponse toResponse(ResidentialLandGalleryImage galleryImage);

    @Named("toResidentialLandGalleryImageEntityList")
    default void partialUpdateList(List<ResidentialLandGalleryImageRequest> residentialLandGalleryImageRequests,
                                   @MappingTarget List<ResidentialLandGalleryImage> residentialLandGalleryImages) {
        if (residentialLandGalleryImages == null) {
            return;
        }

        if (residentialLandGalleryImageRequests == null || residentialLandGalleryImageRequests.isEmpty()) {
            residentialLandGalleryImages.clear();
            return;
        }

        Map<Long, ResidentialLandGalleryImage> existingImagesMap = residentialLandGalleryImages.stream()
                .filter(image -> image.getId() != null)
                .collect(Collectors.toMap(ResidentialLandGalleryImage::getId, image -> image));

        Set<Long> requestIds = residentialLandGalleryImageRequests.stream()
                .map(ResidentialLandGalleryImageRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        residentialLandGalleryImages.removeIf(image ->
                image.getId() != null && !requestIds.contains(image.getId()));

        for (ResidentialLandGalleryImageRequest request : residentialLandGalleryImageRequests) {
            if (request.getId() == null) {
                ResidentialLandGalleryImage newImage = toEntity(request);
                residentialLandGalleryImages.add(newImage);
            } else if (existingImagesMap.containsKey(request.getId())) {
                ResidentialLandGalleryImage existingImage = existingImagesMap.get(request.getId());
                partialUpdate(request, existingImage);
            }
        }
    }
}
