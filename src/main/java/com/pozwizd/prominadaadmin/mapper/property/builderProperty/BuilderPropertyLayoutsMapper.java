package com.pozwizd.prominadaadmin.mapper.property.builderProperty;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts;
import com.pozwizd.prominadaadmin.models.property.builderProperty.request.BuilderPropertyGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.builderProperty.request.BuilderPropertyLayoutsRequest;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyLayoutsResponse;
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
public interface BuilderPropertyLayoutsMapper {

    @Mapping(source = "id", target = "id")
    @Named("toBuilderPropertyLayoutsResponse")
    BuilderPropertyLayoutsResponse toResponse(BuilderPropertyLayouts builderPropertyLayouts);

    @Named("toBuilderPropertyLayoutsResponseList")
    default List<BuilderPropertyLayoutsResponse> toResponseList(List<BuilderPropertyLayouts> builderPropertyLayouts){
        if (builderPropertyLayouts == null || builderPropertyLayouts.isEmpty()) {
            return List.of();
        }
        return builderPropertyLayouts.stream()
                .map(this::toResponse)
                .toList();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Named("partialUpdateBuilderPropertyLayouts")
    @Mapping(
            source = "pathImage1",
            target = "pathImage1",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyLayoutsRequest.getPathImage1() != null" +
                            " && !builderPropertyLayoutsRequest.getPathImage1().isEmpty()" +
                            " && builderPropertyLayoutsRequest.getPathImage1().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathImage2",
            target = "pathImage2",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyLayoutsRequest.getPathImage2() != null" +
                            " && !builderPropertyLayoutsRequest.getPathImage2().isEmpty()" +
                            " && builderPropertyLayoutsRequest.getPathImage2().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathImage3",
            target = "pathImage3",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyLayoutsRequest.getPathImage3() != null" +
                            " && !builderPropertyLayoutsRequest.getPathImage3().isEmpty()" +
                            " && builderPropertyLayoutsRequest.getPathImage3().getSize() > 0" +
                            ")"
    )
    void partialUpdate(BuilderPropertyLayoutsRequest builderPropertyLayoutsRequest,
                           @MappingTarget BuilderPropertyLayouts builderPropertyLayouts);

    @Named("partialUpdateBuilderPropertyLayoutsList")
    default void partialUpdateList(List<BuilderPropertyLayoutsRequest> requests,
                                   @MappingTarget List<BuilderPropertyLayouts> entities){
        if (entities == null) {
            return;
        }
        if (requests == null || requests.isEmpty()) {
            entities.clear();
            return;
        }
        Map<Long, BuilderPropertyLayouts> existing = entities.stream()
                .filter(i -> i.getId() != null)
                .collect(Collectors.toMap(BuilderPropertyLayouts::getId, i -> i));

        Set<Long> requestIds = requests.stream()
                .map(BuilderPropertyLayoutsRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        entities.removeIf(i -> i.getId() != null && !requestIds.contains(i.getId()));

        for (BuilderPropertyLayoutsRequest req : requests) {
            if (req.getId() == null) {
                BuilderPropertyLayouts created = toEntity(req);
                entities.add(created);
            } else if (existing.containsKey(req.getId())) {
                BuilderPropertyLayouts ex = existing.get(req.getId());
                partialUpdate(req, ex);
            }
        }
    }


    @Named("toBuilderPropertyLayoutsEntity")
    @Mapping(
            source = "pathImage1",
            target = "pathImage1",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyLayoutsRequest.getPathImage1() != null" +
                            " && !builderPropertyLayoutsRequest.getPathImage1().isEmpty()" +
                            " && builderPropertyLayoutsRequest.getPathImage1().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathImage2",
            target = "pathImage2",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyLayoutsRequest.getPathImage2() != null" +
                            " && !builderPropertyLayoutsRequest.getPathImage2().isEmpty()" +
                            " && builderPropertyLayoutsRequest.getPathImage2().getSize() > 0" +
                            ")"
    )
    @Mapping(
            source = "pathImage3",
            target = "pathImage3",
            qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "builderPropertyLayoutsRequest.getPathImage3() != null" +
                            " && !builderPropertyLayoutsRequest.getPathImage3().isEmpty()" +
                            " && builderPropertyLayoutsRequest.getPathImage3().getSize() > 0" +
                            ")"
    )
    BuilderPropertyLayouts toEntity(BuilderPropertyLayoutsRequest builderPropertyLayoutsRequest);

    @Named("toBuilderPropertyLayoutsEntityList")
    default List<BuilderPropertyLayouts> toEntityList(List<BuilderPropertyLayoutsRequest> builderPropertyLayoutsRequests){
        if (builderPropertyLayoutsRequests == null || builderPropertyLayoutsRequests.isEmpty()) {
            return List.of();
        }
        return builderPropertyLayoutsRequests.stream()
                .map(this::toEntity)
                .toList();
    }
}
