package com.pozwizd.prominadaadmin.mapper.property.builderProperty;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesGalleryImage;
import com.pozwizd.prominadaadmin.models.property.builderProperty.request.BuilderPropertyGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyGalleryImageResponse;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesGalleryImageRequest;
import com.pozwizd.prominadaadmin.service.FileService;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FileService.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BuilderPropertyGalleryImageMapper {


    @Named("toBuilderPropertyGalleryImageResponse")
    BuilderPropertyGalleryImageResponse toResponse(BuilderPropertyGalleryImage builderPropertyGalleryImage);

    @Named("toBuilderPropertyGalleryImageResponseList")
    List<BuilderPropertyGalleryImageResponse> toResponseList(List<BuilderPropertyGalleryImage> builderPropertyGalleryImages);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Named("partialUpdateBuilderPropertyGalleryImage")
    @Mapping(
            source = "pathImage", target = "pathImage", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyGalleryImageRequest.getPathImage() != null" +
                            " && !builderPropertyGalleryImageRequest.getPathImage().isEmpty()" +
                            " && builderPropertyGalleryImageRequest.getPathImage().getSize() > 0" +
                            ")"
    )
    void partialUpdate(BuilderPropertyGalleryImageRequest builderPropertyGalleryImageRequest,
                           @MappingTarget BuilderPropertyGalleryImage builderPropertyGalleryImage);

    @Named("partialUpdateBuilderPropertyGalleryImageList")
    default void partialUpdateList(
            List<BuilderPropertyGalleryImageRequest> requests,
            @MappingTarget List<BuilderPropertyGalleryImage> entities) {
        if (entities == null) {
            return;
        }
        if (requests == null || requests.isEmpty()) {
            entities.clear();
            return;
        }
        Map<Long, BuilderPropertyGalleryImage> existing = entities.stream()
                .filter(i -> i.getId() != null)
                .collect(Collectors.toMap(BuilderPropertyGalleryImage::getId, i -> i));

        Set<Long> requestIds = requests.stream()
                .map(BuilderPropertyGalleryImageRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        entities.removeIf(i -> i.getId() != null && !requestIds.contains(i.getId()));

        for (BuilderPropertyGalleryImageRequest req : requests) {
            if (req.getId() == null) {
                BuilderPropertyGalleryImage created = toEntity(req);
                entities.add(created);
            } else if (existing.containsKey(req.getId())) {
                BuilderPropertyGalleryImage ex = existing.get(req.getId());
                partialUpdate(req, ex);
            }
        }
    }

    @Named("toBuilderPropertyGalleryImageEntity")
    @Mapping(source = "pathImage", target = "pathImage", qualifiedByName = "uploadFileIfPresent")
    @Mapping(target = "builderProperty", ignore = true)
    BuilderPropertyGalleryImage toEntity(BuilderPropertyGalleryImageRequest builderPropertyGalleryImageRequest);

    @Named("toBuilderPropertyGalleryImageEntityList")
    default List<BuilderPropertyGalleryImage> toEntityList(List<BuilderPropertyGalleryImageRequest> requests){
        if (requests == null || requests.isEmpty()) {
            return List.of();
        }
        return requests.stream()
                .map(this::toEntity)
                .toList();
    }

}
