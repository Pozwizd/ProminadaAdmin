package com.pozwizd.prominadaadmin.mapper.property.investor;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyFile;
import com.pozwizd.prominadaadmin.models.property.investor.request.InvestorPropertyFileRequest;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyFileResponse;
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
public interface InvestorPropertyFileMapper {

    @Named("toInvestorPropertyFileEntity")
    @Mapping(source = "path", target = "path", qualifiedByName = "uploadFile")
    InvestorPropertyFile toEntity(InvestorPropertyFileRequest investorPropertyFileRequest);

    @Named("toInvestorPropertyFileResponse")
    InvestorPropertyFileResponse toResponse(InvestorPropertyFile investorPropertyFile);

    @Named("partialUpdateInvestorPropertyFile")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "investorProperty", ignore = true)
    @Mapping(
            source = "path", target = "path", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "investorPropertyFileRequest.getPath() != null" +
                            " && !investorPropertyFileRequest.getPath().isEmpty()" +
                            " && investorPropertyFileRequest.getPath().getSize() > 0" +
                            ")"
    )
    void partialUpdate(InvestorPropertyFileRequest investorPropertyFileRequest,
                       @MappingTarget InvestorPropertyFile investorPropertyFile);


    @Named("toInvestorPropertyFileEntityList")
    default void partialUpdateList(List<InvestorPropertyFileRequest> investorPropertyFileRequests,
                                   @MappingTarget List<InvestorPropertyFile> investorPropertyFiles) {
        if (investorPropertyFiles == null) {
            return;
        }

        if (investorPropertyFileRequests == null || investorPropertyFileRequests.isEmpty()) {
            investorPropertyFiles.clear();
            return;
        }

        Map<Long, InvestorPropertyFile> existingFilesMap = investorPropertyFiles.stream()
                .filter(file -> file.getId() != null)
                .collect(Collectors.toMap(InvestorPropertyFile::getId, file -> file));

        Set<Long> requestIds = investorPropertyFileRequests.stream()
                .map(InvestorPropertyFileRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        investorPropertyFiles.removeIf(file ->
                file.getId() != null && !requestIds.contains(file.getId()));

        for (InvestorPropertyFileRequest request : investorPropertyFileRequests) {
            if (request.getId() == null) {
                InvestorPropertyFile newFile = toEntity(request);
                investorPropertyFiles.add(newFile);
            } else if (existingFilesMap.containsKey(request.getId())) {
                InvestorPropertyFile existingFile = existingFilesMap.get(request.getId());
                partialUpdate(request, existingFile);
            }
        }
    }


}