package com.pozwizd.prominadaadmin.mapper.property.investor;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyGalleryImage;
import com.pozwizd.prominadaadmin.models.property.investor.request.InvestorPropertyGalleryImageRequest;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyGalleryImageResponse;
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
public interface InvestorPropertyGalleryImageMapper {

    @Named("toInvestorPropertyGalleryImageEntity")
    @Mapping(source = "pathImage", target = "pathImage", qualifiedByName = "uploadFile")
    @Mapping(target = "investorProperty", ignore = true)
    InvestorPropertyGalleryImage toEntity(InvestorPropertyGalleryImageRequest investorPropertyGalleryImageRequest);

    @Named("partialUpdateInvestorPropertyGalleryImage")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "investorProperty", ignore = true)
    @Mapping(
            source = "pathImage", target = "pathImage", qualifiedByName = "uploadFileIfPresent",
            conditionExpression =
                    "java(" +
                            "investorPropertyGalleryImageRequest.getPathImage() != null" +
                            " && !investorPropertyGalleryImageRequest.getPathImage().isEmpty()" +
                            " && investorPropertyGalleryImageRequest.getPathImage().getSize() > 0" +
                            ")"
    )
    void partialUpdate(InvestorPropertyGalleryImageRequest investorPropertyGalleryImageRequest,
                       @MappingTarget InvestorPropertyGalleryImage investorPropertyGalleryImage);

    @Named("toInvestorPropertyGalleryImageResponse")
    InvestorPropertyGalleryImageResponse toResponse(InvestorPropertyGalleryImage galleryImage);

    @Named("toInvestorPropertyGalleryImageEntityList")
    default void partialUpdateList(List<InvestorPropertyGalleryImageRequest> investorPropertyGalleryImageRequests,
                                   @MappingTarget List<InvestorPropertyGalleryImage> investorPropertyGalleryImages) {
        if (investorPropertyGalleryImages == null) {
            return;
        }

        if (investorPropertyGalleryImageRequests == null || investorPropertyGalleryImageRequests.isEmpty()) {
            investorPropertyGalleryImages.clear();
            return;
        }

        Map<Long, InvestorPropertyGalleryImage> existingImagesMap = investorPropertyGalleryImages.stream()
                .filter(image -> image.getId() != null)
                .collect(Collectors.toMap(InvestorPropertyGalleryImage::getId, image -> image));

        Set<Long> requestIds = investorPropertyGalleryImageRequests.stream()
                .map(InvestorPropertyGalleryImageRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        investorPropertyGalleryImages.removeIf(image ->
                image.getId() != null && !requestIds.contains(image.getId()));

        for (InvestorPropertyGalleryImageRequest request : investorPropertyGalleryImageRequests) {
            if (request.getId() == null) {
                InvestorPropertyGalleryImage newImage = toEntity(request);
                investorPropertyGalleryImages.add(newImage);
            } else if (existingImagesMap.containsKey(request.getId())) {
                InvestorPropertyGalleryImage existingImage = existingImagesMap.get(request.getId());
                partialUpdate(request, existingImage);
            }
        }
    }
}