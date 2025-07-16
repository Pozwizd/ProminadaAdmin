package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Banner;
import com.pozwizd.prominadaadmin.entity.ImageBanner;
import com.pozwizd.prominadaadmin.models.banner.ImageBannerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageBannerService {

    // Entity ============================================================

    ImageBanner save(ImageBanner imageBanner);


    // DTOs ==============================================================


    ImageBanner create(ImageBannerRequest request, Banner banner);

    ImageBanner update(ImageBannerRequest request);

    void delete(Long id);

    void deleteByBannerId(Long bannerId);

    List<ImageBanner> findByBannerId(Long bannerId);

    void validateImageBannersBelongToBanner(List<Long> imageBannerIds, Long bannerId);
}
