package com.pozwizd.prominadaadmin.mapper.property.residentialLand;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.models.property.residentialLand.response.ResidentialLandGalleryImageRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResidentialLandGalleryImageMapper {
    ResidentialLandGalleryImage toEntity(ResidentialLandGalleryImageRequest residentialLandGalleryImageRequest);

    @Named( "partialUpdateResidentialLandGalleryImage")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResidentialLandGalleryImage partialUpdate(ResidentialLandGalleryImageRequest residentialLandGalleryImageRequest,
                                              @MappingTarget ResidentialLandGalleryImage residentialLandGalleryImage);
}