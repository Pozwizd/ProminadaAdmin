package com.pozwizd.prominadaadmin.mapper.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyGalleryImageResponse;
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
public interface SecondaryPropertyGalleryImageMapper {

    @Named("toSecondaryPropertyGalleryImageEntity")
    @Mapping(source = "pathImage", target = "pathImage", qualifiedByName = "uploadFile")
    @Mapping(target = "secondaryProperty", ignore = true)
    SecondaryPropertyGalleryImage toEntity(SecondaryPropertyGalleryImageRequest secondaryPropertyGalleryImageRequest);

    @Named("toSecondaryPropertyGalleryImageEntityList")
    default List<SecondaryPropertyGalleryImage> toEntityList(List<SecondaryPropertyGalleryImageRequest> secondaryPropertyGalleryImageRequests) {
        if (secondaryPropertyGalleryImageRequests == null || secondaryPropertyGalleryImageRequests.isEmpty()) {
            return List.of();
        }
        return secondaryPropertyGalleryImageRequests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }


    @Named("toSecondaryPropertyGalleryImageResponse")
    SecondaryPropertyGalleryImageResponse toResponse(SecondaryPropertyGalleryImage galleryImage);

    @Named("toSecondaryPropertyGalleryImageResponseList")
    default List<SecondaryPropertyGalleryImageResponse> toResponseList(List<SecondaryPropertyGalleryImage> secondaryPropertyGalleryImages) {
        if (secondaryPropertyGalleryImages == null || secondaryPropertyGalleryImages.isEmpty()) {
            return List.of();
        }
        return secondaryPropertyGalleryImages.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Named("partialUpdateSecondaryPropertyGalleryImage")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "secondaryProperty", ignore = true)
    @Mapping(
            source = "pathImage", target = "pathImage", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "secondaryPropertyGalleryImageRequest.getPathImage() != null" +
                            " && !secondaryPropertyGalleryImageRequest.getPathImage().isEmpty()" +
                            " && secondaryPropertyGalleryImageRequest.getPathImage().getSize() > 0" +
                            ")"
    )
    void partialUpdate(SecondaryPropertyGalleryImageRequest secondaryPropertyGalleryImageRequest,
                       @MappingTarget SecondaryPropertyGalleryImage secondaryPropertyGalleryImage);


    @Named("partialUpdateSecondaryPropertyGalleryImageList")
    default void partialUpdateList(List<SecondaryPropertyGalleryImageRequest> secondaryPropertyGalleryImageRequests,
                                   @MappingTarget List<SecondaryPropertyGalleryImage> secondaryPropertyGalleryImages) {
        if (secondaryPropertyGalleryImages == null) {
            return;
        }

        if (secondaryPropertyGalleryImageRequests == null || secondaryPropertyGalleryImageRequests.isEmpty()) {
            secondaryPropertyGalleryImages.clear();
            return;
        }

        Map<Long, SecondaryPropertyGalleryImage> existingImagesMap = secondaryPropertyGalleryImages.stream()
                .filter(image -> image.getId() != null)
                .collect(Collectors.toMap(SecondaryPropertyGalleryImage::getId, image -> image));

        Set<Long> requestIds = secondaryPropertyGalleryImageRequests.stream()
                .map(SecondaryPropertyGalleryImageRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        secondaryPropertyGalleryImages.removeIf(image ->
                image.getId() != null && !requestIds.contains(image.getId()));

        for (SecondaryPropertyGalleryImageRequest request : secondaryPropertyGalleryImageRequests) {
            if (request.getId() == null) {
                SecondaryPropertyGalleryImage newImage = toEntity(request);
                secondaryPropertyGalleryImages.add(newImage);
            } else if (existingImagesMap.containsKey(request.getId())) {
                SecondaryPropertyGalleryImage existingImage = existingImagesMap.get(request.getId());
                partialUpdate(request, existingImage);
            }
        }
    }
}
