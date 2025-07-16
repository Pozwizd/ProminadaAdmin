package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.Banner;
import com.pozwizd.prominadaadmin.entity.ImageBanner;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.ImageBannerMapper;
import com.pozwizd.prominadaadmin.models.banner.ImageBannerRequest;
import com.pozwizd.prominadaadmin.repository.primary.ImageBannerRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.ImageBannerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageBannerServiceImpl implements ImageBannerService {

    private final ImageBannerRepository imageBannerRepository;
    private final FileService fileService;
    private final ImageBannerMapper imageBannerMapper;



    @Override
    public ImageBanner save(ImageBanner imageBanner) {
        ImageBanner savedImageBanner = new ImageBanner();
        savedImageBanner.setName(imageBanner.getName());
        savedImageBanner.setPriority(imageBanner.getPriority());
        savedImageBanner.setPathImage(imageBanner.getPathImage());
        savedImageBanner.setBanner(imageBanner.getBanner());
        imageBannerRepository.save(savedImageBanner);
        return imageBannerRepository.save(savedImageBanner);
    }

    @Override
    public ImageBanner create(ImageBannerRequest request, Banner banner) {
        try {
            ImageBanner imageBanner = imageBannerMapper.toEntity(request, fileService);
            imageBanner.setBanner(banner);

            ImageBanner saved = imageBannerRepository.save(imageBanner);
            log.info("Создан ImageBanner с ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            log.error("Ошибка создания ImageBanner для баннера ID: {}", banner.getId(), e);
            throw new OperationException("Не удалось создать изображение", e.getMessage());
        }
    }

    @Override
    public ImageBanner update(ImageBannerRequest request) {
        ImageBanner existing = imageBannerRepository.findById(request.getId())
                .orElseThrow(() -> new OperationException(
                        "ImageBanner с ID " + request.getId() + " не найден",
                        "Изображение не найдено"
                ));

        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getPriority() != null) {
            existing.setPriority(request.getPriority());
        }

        if (request.getPathImage() != null && !request.getPathImage().isEmpty()) {
            try {
                if (existing.getPathImage() != null) {
                    fileService.deleteFile(existing.getPathImage());
                }

                String newPath = fileService.uploadFile(request.getPathImage());
                existing.setPathImage(newPath);
            } catch (IOException e) {
                throw new OperationException("Ошибка загрузки файла", e.getMessage());
            }
        }

        ImageBanner saved = imageBannerRepository.save(existing);
        log.info("Обновлен ImageBanner с ID: {}", saved.getId());
        return saved;
    }

    @Override
    public void delete(Long id) {
        ImageBanner imageBanner = imageBannerRepository.findById(id)
                .orElseThrow(() -> new OperationException(
                        "ImageBanner с ID " + id + " не найден",
                        "Изображение не найдено"
                ));

        if (imageBanner.getPathImage() != null) {
            try {
                fileService.deleteFile(imageBanner.getPathImage());
            } catch (Exception e) {
                log.warn("Не удалось удалить файл: {}", imageBanner.getPathImage(), e);
            }
        }

        imageBannerRepository.delete(imageBanner);
        log.info("Удален ImageBanner с ID: {}", id);
    }

    @Override
    public void deleteByBannerId(Long bannerId) {
        List<ImageBanner> imageBanners = imageBannerRepository.findByBannerId(bannerId);

        for (ImageBanner imageBanner : imageBanners) {
            delete(imageBanner.getId());
        }

        log.info("Удалены все ImageBanner для баннера ID: {}", bannerId);
    }

    @Override
    public List<ImageBanner> findByBannerId(Long bannerId) {
        return imageBannerRepository.findByBannerId(bannerId);
    }

    @Override
    public void validateImageBannersBelongToBanner(List<Long> imageBannerIds, Long bannerId) {
        List<ImageBanner> imageBanners = imageBannerRepository.findAllById(imageBannerIds);

        for (ImageBanner imageBanner : imageBanners) {
            if (!bannerId.equals(imageBanner.getBanner().getId())) {
                throw new OperationException(
                        "ImageBanner с ID " + imageBanner.getId() + " не принадлежит баннеру с ID " + bannerId,
                        "Нарушение целостности данных"
                );
            }
        }
    }
}
