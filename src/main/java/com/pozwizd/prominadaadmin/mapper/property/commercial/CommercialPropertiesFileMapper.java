package com.pozwizd.prominadaadmin.mapper.property.commercial;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesFile;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesFileRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesFileResponse;
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
public interface CommercialPropertiesFileMapper {

    @Named("toCommercialPropertiesFileEntity")
    @Mapping(source = "path", target = "path", qualifiedByName = "uploadFile")
    @Mapping(target = "commercialProperties", ignore = true)
    CommercialPropertiesFile toEntity(CommercialPropertiesFileRequest commercialPropertiesFileRequest);

    @Named("toCommercialPropertiesFileEntityList")
    default List<CommercialPropertiesFile> toEntityList(List<CommercialPropertiesFileRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return List.of();
        }
        return requests.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }


    @Named("toCommercialPropertiesFileResponse")
    CommercialPropertiesFileResponse toResponse(CommercialPropertiesFile commercialPropertiesFile);

    @Named("toCommercialPropertiesFileResponseList")
    default List<CommercialPropertiesFileResponse> toResponseList(List<CommercialPropertiesFile> commercialPropertiesFiles) {
        if (commercialPropertiesFiles == null || commercialPropertiesFiles.isEmpty()) {
            return List.of();
        }
        return commercialPropertiesFiles.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Named("partialUpdateCommercialPropertiesFile")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commercialProperties", ignore = true)
    @Mapping(
            source = "path", target = "path", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "commercialPropertiesFileRequest.getPath() != null" +
                            " && !commercialPropertiesFileRequest.getPath().isEmpty()" +
                            " && commercialPropertiesFileRequest.getPath().getSize() > 0" +
                            ")"
    )
    void partialUpdate(CommercialPropertiesFileRequest commercialPropertiesFileRequest,
                       @MappingTarget CommercialPropertiesFile commercialPropertiesFile);

    @Named("partialUpdateCommercialPropertiesFileList")
    default void partialUpdateList(List<CommercialPropertiesFileRequest> requests,
                                   @MappingTarget List<CommercialPropertiesFile> entities) {
        if (entities == null) {
            return;
        }
        if (requests == null || requests.isEmpty()) {
            entities.clear();
            return;
        }
        Map<Long, CommercialPropertiesFile> existing = entities.stream()
                .filter(f -> f.getId() != null)
                .collect(Collectors.toMap(CommercialPropertiesFile::getId, f -> f));

        Set<Long> requestIds = requests.stream()
                .map(CommercialPropertiesFileRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        entities.removeIf(f -> f.getId() != null && !requestIds.contains(f.getId()));

        for (CommercialPropertiesFileRequest req : requests) {
            if (req.getId() == null) {
                CommercialPropertiesFile created = toEntity(req);
                entities.add(created);
            } else if (existing.containsKey(req.getId())) {
                CommercialPropertiesFile existingEntity = existing.get(req.getId());
                partialUpdate(req, existingEntity);
            }
        }
    }
}
