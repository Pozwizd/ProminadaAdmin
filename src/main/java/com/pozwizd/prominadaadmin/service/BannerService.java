package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Banner;
import com.pozwizd.prominadaadmin.models.banner.BannerRequest;
import com.pozwizd.prominadaadmin.models.banner.BannerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BannerService {

    Banner getById(Long id);

    Banner save(Banner banner);

    void deleteById(Long id);

    List<BannerResponse> getAllBannerResponse();

    Banner save(BannerRequest bannerRequest);

    BannerResponse update(BannerRequest bannerRequest);

}
