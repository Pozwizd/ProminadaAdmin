package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.Banner;
import com.pozwizd.prominadaadmin.entity.ImageBanner;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.BannerMapper;
import com.pozwizd.prominadaadmin.mapper.ImageBannerMapper;
import com.pozwizd.prominadaadmin.models.banner.BannerRequest;
import com.pozwizd.prominadaadmin.models.banner.BannerResponse;
import com.pozwizd.prominadaadmin.models.banner.ImageBannerRequest;
import com.pozwizd.prominadaadmin.repository.primary.BannerRepository;
import com.pozwizd.prominadaadmin.repository.primary.ImageBannerRepository;
import com.pozwizd.prominadaadmin.service.BannerService;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.ImageBannerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class BannerServiceImp implements BannerService {

    private final BannerRepository bannerRepository;
    private final ImageBannerService imageBannerService;

    private final BannerMapper bannerMapper;
    private final ImageBannerMapper imageBannerMapper;

    private final FileService fileService;


    // Entity ============================================================
    @Override
    public Banner getById(Long id) {
        try {
            Banner banner = bannerRepository.findById(id).orElse(null);
            if (banner == null) {
                log.warn("Баннер с ID {} не найден", id);
            } else {
                log.info("Баннер с ID {} успешно получен", id);
            }
            return banner;
        } catch (Exception e) {
            log.error("Ошибка при получении баннера с ID {}", id, e);
            throw new OperationException("получении баннера с ID " + id, e.getMessage());
        }
    }
    @Override
    public Banner save(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public List<BannerResponse> getAllBannerResponse() {
        try {
            List<BannerResponse> bannerResponses = bannerMapper.toResponse(bannerRepository.findAll());
            log.info("Успешно получено {} баннеров", bannerResponses.size());
            return bannerResponses;
        } catch (Exception e) {
            log.error("Ошибка при получении списка баннеров", e);
            throw new OperationException("получении списка баннеров", e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            log.info("Удаление баннера с ID {}", id);
            bannerRepository.deleteById(id);
            log.info("Баннер с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удалении баннера с ID {}", id, e);
            throw new OperationException("удалении баннера с ID " + id, e.getMessage());
        }
    }
    // Entity ============================================================


    // DTOs ============================================================
    @Override
    public Banner save(BannerRequest bannerRequest) {
        try {
            log.info("Начало сохранения баннера: {}", bannerRequest);

            Banner banner = bannerMapper.toEntity(bannerRequest);
            List<ImageBanner> imageBanners = imageBannerMapper
                    .toEntity(bannerRequest.getImageBannersRequest(), fileService);
            for (ImageBanner imageBanner : imageBanners) {
                imageBanner.setBanner(banner);
            }
            banner.setImageBanners(imageBanners);
            Banner savedBanner = bannerRepository.save(banner);

            log.info("Баннер с ID {} успешно сохранен", savedBanner.getId());
            return savedBanner;
        } catch (Exception e) {
            log.error("Ошибка при сохранении баннера", e);
            throw new OperationException("сохранении баннера", e.getMessage());
        }
    }

    @Override
    @Transactional
    public BannerResponse update(BannerRequest bannerRequest) {
        try {
            if (bannerRequest == null || bannerRequest.getId() == null) {
                throw new OperationException("ID баннера обязателен для обновления", "Некорректные входные данные");
            }

            Banner oldBanner = bannerRepository.findById(bannerRequest.getId())
                    .orElseThrow(() -> new OperationException(
                            "баннер с ID " + bannerRequest.getId() + " не найден",
                            "Не удалось найти баннер для обновления"
                    ));

            updateBannerFields(oldBanner, bannerRequest);

            if (bannerRequest.getImageBannersRequest() != null) {
                updateImageBanners(oldBanner, bannerRequest.getImageBannersRequest());
            }

            Banner savedBanner = bannerRepository.save(oldBanner);
            log.info("Баннер с ID {} успешно обновлен", savedBanner.getId());

            return bannerMapper.toResponse(savedBanner);

        } catch (OperationException e) {
            log.error("Бизнес-логическая ошибка при обновлении баннера: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Непредвиденная ошибка при обновлении баннера с ID {}", bannerRequest.getId(), e);
            throw new OperationException("Ошибка при сохранении баннера", e.getMessage());
        }
    }

    private void updateBannerFields(Banner banner, BannerRequest request) {
        if (request.getName() != null) {
            banner.setName(request.getName());
        }
        if (request.getStatus() != null) {
            banner.setStatus(request.getStatus());
        }
    }

    private void updateImageBanners(Banner banner, List<ImageBannerRequest> requests) {

        List<ImageBanner> currentImages = imageBannerService.findByBannerId(banner.getId());

        Set<Long> requestIds = requests.stream()
                .map(ImageBannerRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (!requestIds.isEmpty()) {
            imageBannerService.validateImageBannersBelongToBanner(
                    new ArrayList<>(requestIds), banner.getId()
            );
        }

        currentImages.stream()
                .filter(img -> img.getId() != null && !requestIds.contains(img.getId()))
                .forEach(img -> {
                    imageBannerService.delete(img.getId());
                    banner.removeImageBanner(img);
                });

        for (ImageBannerRequest request : requests) {
            if (request.getId() != null) {
                ImageBanner updated = imageBannerService.update(request);

                banner.getImageBanners().replaceAll(img ->
                        img.getId().equals(updated.getId()) ? updated : img
                );
            } else {
                ImageBanner created = imageBannerService.create(request, banner);
                banner.addImageBanner(created);
            }
        }
    }
    // DTOs ============================================================


}

