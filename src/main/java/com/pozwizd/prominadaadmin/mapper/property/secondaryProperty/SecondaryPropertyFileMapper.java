package com.pozwizd.prominadaadmin.mapper.property.secondaryProperty;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyFileRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyFileResponse;
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
public interface SecondaryPropertyFileMapper {

    @Named("toSecondaryPropertyFileEntity")
    @Mapping(source = "filePath", target = "path", qualifiedByName = "uploadFile")
    @Mapping(target = "secondaryProperty", ignore = true)
    SecondaryPropertyFile toEntity(SecondaryPropertyFileRequest secondaryPropertyFileRequest);

    @Named("toSecondaryPropertyFileResponse")
    SecondaryPropertyFileResponse toResponse(SecondaryPropertyFile secondaryPropertyFile);

    @Named("partialUpdateSecondaryPropertyFile")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "secondaryProperty", ignore = true)
    @Mapping(
            source = "filePath", target = "path", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "secondaryPropertyFileRequest.getFilePath() != null" +
                            " && !secondaryPropertyFileRequest.getFilePath().isEmpty()" +
                            " && secondaryPropertyFileRequest.getFilePath().getSize() > 0" +
                            ")"
    )
    void partialUpdate(SecondaryPropertyFileRequest secondaryPropertyFileRequest,
                       @MappingTarget SecondaryPropertyFile secondaryPropertyFile);


    @Named("partialUpdateSecondaryPropertyFileList")
    default void partialUpdateList(List<SecondaryPropertyFileRequest> secondaryPropertyFileRequests,
                                   @MappingTarget List<SecondaryPropertyFile> secondaryPropertyFiles) {
        if (secondaryPropertyFiles == null) {
            return;
        }

        if (secondaryPropertyFileRequests == null || secondaryPropertyFileRequests.isEmpty()) {
            secondaryPropertyFiles.clear();
            return;
        }

        Map<Long, SecondaryPropertyFile> existingFilesMap = secondaryPropertyFiles.stream()
                .filter(file -> file.getId() != null)
                .collect(Collectors.toMap(SecondaryPropertyFile::getId, file -> file));

        Set<Long> requestIds = secondaryPropertyFileRequests.stream()
                .map(SecondaryPropertyFileRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        secondaryPropertyFiles.removeIf(file ->
                file.getId() != null && !requestIds.contains(file.getId()));

        for (SecondaryPropertyFileRequest request : secondaryPropertyFileRequests) {
            if (request.getId() == null) {
                SecondaryPropertyFile newFile = toEntity(request);
                secondaryPropertyFiles.add(newFile);
            } else if (existingFilesMap.containsKey(request.getId())) {
                SecondaryPropertyFile existingFile = existingFilesMap.get(request.getId());
                partialUpdate(request, existingFile);
            }
        }
    }
}
