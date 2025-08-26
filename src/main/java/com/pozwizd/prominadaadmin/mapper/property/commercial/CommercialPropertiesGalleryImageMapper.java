package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesGalleryImage;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesGalleryImageResponse;
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
public interface CommercialPropertiesGalleryImageMapper {

    @Named("toCommercialPropertiesGalleryImageEntity")
    @Mapping(source = "pathImage", target = "pathImage", qualifiedByName = "uploadFile")
    @Mapping(target = "commercialProperties", ignore = true)
    CommercialPropertiesGalleryImage toEntity(CommercialPropertiesGalleryImageRequest
                                                      commercialPropertiesGalleryImageRequest);

    @Named("toCommercialPropertiesGalleryImageEntityList")
    default List<CommercialPropertiesGalleryImage> toEntityList(List<CommercialPropertiesGalleryImageRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return List.of();
        }
        return requests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Named("toCommercialPropertiesGalleryImageResponse")
    CommercialPropertiesGalleryImageResponse toResponse(CommercialPropertiesGalleryImage
                                                               commercialPropertiesGalleryImage);

    @Named("toCommercialPropertiesGalleryImageResponseList")
    default List<CommercialPropertiesGalleryImageResponse> toResponseList(List<CommercialPropertiesGalleryImage> commercialPropertiesGalleryImages) {
        if (commercialPropertiesGalleryImages == null || commercialPropertiesGalleryImages.isEmpty()) {
            return List.of();
        }
        return commercialPropertiesGalleryImages.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Named("partialUpdateCommercialPropertiesGalleryImage")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commercialProperties", ignore = true)
    @Mapping(
            source = "pathImage", target = "pathImage", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "commercialPropertiesGalleryImageRequest.getPathImage() != null" +
                            " && !commercialPropertiesGalleryImageRequest.getPathImage().isEmpty()" +
                            " && commercialPropertiesGalleryImageRequest.getPathImage().getSize() > 0" +
                            ")"
    )
    void partialUpdate(CommercialPropertiesGalleryImageRequest
                                                           commercialPropertiesGalleryImageRequest,
                       @MappingTarget CommercialPropertiesGalleryImage
                                                           commercialPropertiesGalleryImage);

    @Named("partialUpdateCommercialPropertiesGalleryImageList")
    default void partialUpdateList(List<CommercialPropertiesGalleryImageRequest> requests,
                                   @MappingTarget List<CommercialPropertiesGalleryImage> entities) {
        if (entities == null) {
            return;
        }
        if (requests == null || requests.isEmpty()) {
            entities.clear();
            return;
        }
        Map<Long, CommercialPropertiesGalleryImage> existing = entities.stream()
                .filter(i -> i.getId() != null)
                .collect(Collectors.toMap(CommercialPropertiesGalleryImage::getId, i -> i));

        Set<Long> requestIds = requests.stream()
                .map(CommercialPropertiesGalleryImageRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        entities.removeIf(i -> i.getId() != null && !requestIds.contains(i.getId()));

        for (CommercialPropertiesGalleryImageRequest req : requests) {
            if (req.getId() == null) {
                CommercialPropertiesGalleryImage created = toEntity(req);
                entities.add(created);
            } else if (existing.containsKey(req.getId())) {
                CommercialPropertiesGalleryImage ex = existing.get(req.getId());
                partialUpdate(req, ex);
            }
        }
    }
}