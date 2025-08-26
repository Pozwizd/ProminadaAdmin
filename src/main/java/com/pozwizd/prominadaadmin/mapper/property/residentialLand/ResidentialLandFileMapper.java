package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandFile;
import com.pozwizd.prominadaadmin.models.property.residentialLand.request.ResidentialLandFileRequest;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandFileResponse;
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
public interface ResidentialLandFileMapper {

    @Named("toResidentialLandFileEntity")
    @Mapping(source = "filePath", target = "filePath", qualifiedByName = "uploadFile")
    @Mapping(target = "residentialLand", ignore = true)
    ResidentialLandFile toEntity(ResidentialLandFileRequest residentialLandFileRequest);


    @Named("toResidentialLandFileResponse")
    ResidentialLandFileResponse toResponse(ResidentialLandFile residentialLandFile);

    @Named("partialUpdateResidentialLandFile")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "residentialLand", ignore = true)
    @Mapping(
            source = "filePath", target = "filePath", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "residentialLandFileRequest.getFilePath() != null" +
                            " && !residentialLandFileRequest.getFilePath().isEmpty()" +
                            " && residentialLandFileRequest.getFilePath().getSize() > 0" +
                            ")"
    )
    void partialUpdate(ResidentialLandFileRequest residentialLandFileRequest,
                                      @MappingTarget ResidentialLandFile residentialLandFile);


    @Named("toResidentialLandFileEntityList")
    default void partialUpdateList(List<ResidentialLandFileRequest> residentialLandFileRequests,
                                   @MappingTarget List<ResidentialLandFile> residentialLandFiles) {
        if (residentialLandFiles == null) {
            return;
        }

        if (residentialLandFileRequests == null || residentialLandFileRequests.isEmpty()) {
            residentialLandFiles.clear();
            return;
        }

        Map<Long, ResidentialLandFile> existingFilesMap = residentialLandFiles.stream()
                .filter(file -> file.getId() != null)
                .collect(Collectors.toMap(ResidentialLandFile::getId, file -> file));

        Set<Long> requestIds = residentialLandFileRequests.stream()
                .map(ResidentialLandFileRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        residentialLandFiles.removeIf(file ->
                file.getId() != null && !requestIds.contains(file.getId()));

        for (ResidentialLandFileRequest request : residentialLandFileRequests) {
            if (request.getId() == null) {
                ResidentialLandFile newFile = toEntity(request);
                residentialLandFiles.add(newFile);
            } else if (existingFilesMap.containsKey(request.getId())) {
                ResidentialLandFile existingFile = existingFilesMap.get(request.getId());
                partialUpdate(request, existingFile);
            }
        }
    }


}