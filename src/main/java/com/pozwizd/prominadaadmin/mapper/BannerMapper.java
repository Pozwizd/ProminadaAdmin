package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Banner;
import com.pozwizd.prominadaadmin.models.banner.BannerRequest;
import com.pozwizd.prominadaadmin.models.banner.BannerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BannerMapper {

    // to Entity
    @Mapping(source = "imageBannersRequest", target = "imageBanners", ignore = true)
    Banner toEntity(BannerRequest bannerRequest);

    default List<Banner> toEntity(List<BannerRequest> bannerRequests) {
        return bannerRequests.stream().map(this::toEntity).toList();
    }

    // to Response
    @Mapping(source = "imageBanners", target = "imageBannersRequest")
    BannerResponse toResponse(Banner banner);

    default List<BannerResponse> toResponse(List<Banner> banners) {
        return banners.stream().map(this::toResponse).toList();
    }
}