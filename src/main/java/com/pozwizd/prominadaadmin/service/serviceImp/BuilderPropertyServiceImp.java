package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts;
import com.pozwizd.prominadaadmin.mapper.BuilderPropertyMapper;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDtoForTable;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyLayoutDto;
import com.pozwizd.prominadaadmin.models.media.MediaDtoDrop;
import com.pozwizd.prominadaadmin.repository.BuilderPropertyRepository;
import com.pozwizd.prominadaadmin.service.BuilderPropertyService;
import com.pozwizd.prominadaadmin.service.ImageService;
import com.pozwizd.prominadaadmin.service.RegDistrictService;
import com.pozwizd.prominadaadmin.service.TopozoneService;
import com.pozwizd.prominadaadmin.specification.BuilderPropertySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuilderPropertyServiceImp implements BuilderPropertyService {
    private final BuilderPropertyRepository builderPropertyRepository;
    private final BuilderPropertyMapper builderPropertyMapper;
    private final ImageService imageService;
    private final RegDistrictService regDistrictService;
    private final DistrictServiceImp districtServiceImp;
    private final CityServiceImp cityServiceImp;
    private final TopozoneService topozoneService;

    @Value("${file.upload.dir}")
    private String contextPath;

    @Override
    public Page<BuilderPropertyDtoForTable> getPageableBuilders(int page, Integer size, BuilderPropertyDtoForTable builderPropertyDto) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return builderPropertyMapper.toDto(builderPropertyRepository.findAll(BuilderPropertySpecification.search(builderPropertyDto),
                pageRequest));
    }

    @Override
    public void deleteById(Long id) {
        builderPropertyRepository.deleteById(id);
    }

    @Override
    public BuilderProperty getById(Long id) {
        return builderPropertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Builder property with id: " + id + " was not found!"));
    }

    @Override
    public BuilderPropertyDto getByIdInDto(Long id) {
        return builderPropertyMapper.toDto(getById(id));
    }


    @Override
    public BuilderProperty save(BuilderProperty builderProperty) {
        return builderPropertyRepository.save(builderProperty);
    }

    @SneakyThrows
    @Override
    public BuilderProperty save(BuilderPropertyDto dto) {
//        LogUtil.logInfo("Starting to save ClientCategoryRequestForAdd: " + dto);
        if (dto.getFilesDto() != null) {
            for (MediaDtoDrop m : dto.getFilesDto()) {
                if (m.getFile() != null) {
                    String generatedPath = contextPath + "/builder/" + imageService.generateFileName(m.getFile());
                    m.setPathImage(generatedPath);
                }
            }
        }

        if (dto.getChessPlanFile() != null) {
            String generatedPath = contextPath + "/builder/" + imageService.generateFileName(dto.getChessPlanFile());
            dto.setPathToChessPlanFile(generatedPath);
//            LogUtil.logInfo("Generated image path for category: " + generatedPath);
        }
        if (dto.getPriceFile() != null) {
            String generatedPath = contextPath + "/builder/" + imageService.generateFileName(dto.getPriceFile());
            dto.setPathToPriceFile(generatedPath);
//            LogUtil.logInfo("Generated image path for category: " + generatedPath);
        }
        if (dto.getMortgageConditionsFile() != null) {
            String generatedPath = contextPath + "/builder/" + imageService.generateFileName(dto.getMortgageConditionsFile());
            dto.setPathToMortgageConditionsFile(generatedPath);
//            LogUtil.logInfo("Generated image path for category: " + generatedPath);
        }

        if (dto.getFilesDto() != null) {
            for (MediaDtoDrop m : dto.getFilesDto()) {
                if (m.getFile() != null) {
                    String generatedPath = contextPath + "/gallery/" + imageService.generateFileName(m.getFile());
                    m.setPathImage(generatedPath);
                    m.setName(m.getFile().getOriginalFilename());
                }
                imageService.save(m.getFile(), m.getPathImage());
            }
        }

        if (dto.getLayoutDto() != null) {
            for (BuilderPropertyLayoutDto m : dto.getLayoutDto()) {
                if (m.getFile1() != null) {
                    String generatedPath = contextPath + "/layout/" + imageService.generateFileName(m.getFile1());
                    m.setPathImage1(generatedPath);
                    m.setName(m.getFile1().getOriginalFilename());
                }
                if (m.getFile2() != null) {
                    String generatedPath = contextPath + "/layout/" + imageService.generateFileName(m.getFile2());
                    m.setPathImage2(generatedPath);
                    m.setName(m.getFile2().getOriginalFilename());
                }
                if (m.getFile3() != null) {
                    String generatedPath = contextPath + "/layout/" + imageService.generateFileName(m.getFile3());
                    m.setPathImage3(generatedPath);
                    m.setName(m.getFile3().getOriginalFilename());
                }
            }
        }

        if (dto.getId() != null) {
//            LogUtil.logInfo("ClientCategoryId provided: " + dto.getClientsCategoryId());
            BuilderProperty builderProperty = getById(dto.getId());
//            LogUtil.logInfo("Found ClientCategory by ID: " + builderProperty);

            if (dto.getPathToChessPlanFile() != null && dto.getPathToChessPlanFile().isEmpty()) {
//                LogUtil.logInfo("Deleting old image for category with ID: " + dto.getClientsCategoryId());
                imageService.deleteByPath(builderProperty.getPathToChessPlanFile());
            }
            if (dto.getPathToMortgageConditionsFile() != null && dto.getPathToMortgageConditionsFile().isEmpty()) {
//                LogUtil.logInfo("Deleting old image for category with ID: " + dto.getClientsCategoryId());
                imageService.deleteByPath(builderProperty.getPathToMortgageConditionsFile());
            }
            if (dto.getPathToPriceFile() != null && dto.getPathToPriceFile().isEmpty()) {
//                LogUtil.logInfo("Deleting old image for category with ID: " + dto.getClientsCategoryId());
                imageService.deleteByPath(builderProperty.getPathToPriceFile());
            }

            builderProperty.setPathToChessPlanFile(dto.getPathToChessPlanFile());
//            LogUtil.logInfo("Updated ClientCategory with new paths.");
        }

        imageService.save(dto.getChessPlanFile(), dto.getPathToChessPlanFile());
        imageService.save(dto.getMortgageConditionsFile(), dto.getPathToMortgageConditionsFile());
        imageService.save(dto.getPriceFile(), dto.getPathToPriceFile());
//        LogUtil.logInfo("Images saved successfully for category.");

        BuilderProperty builderProperty = builderPropertyMapper.toEntityFromRequest(dto);
        builderProperty.setRegDistrict(regDistrictService.getById(Long.parseLong(dto.getRegDistrictId())));
        builderProperty.setCity(cityServiceImp.getById(Long.parseLong(dto.getCityId())));
        builderProperty.setDistinct(districtServiceImp.getById(Long.parseLong(dto.getDistrictId())));
        builderProperty.setTopozone(topozoneService.getById(Long.parseLong(dto.getTopozoneId())));

        if (dto.getLayoutDto() != null) {
            List<BuilderPropertyLayouts> layouts = dto.getLayoutDto().stream().map(d -> builderPropertyMapper.toEntityFromRequest(d, builderProperty)).toList();
            builderProperty.setBuilderPropertyLayouts(layouts);
        }
        if (dto.getFilesDto() != null) {
            List<BuilderPropertyGalleryImage> gallery = dto.getFilesDto().stream().map(d -> builderPropertyMapper.toEntityFromRequest(d, builderProperty)).toList();
            builderProperty.setBuilderPropertyGalleryImages(gallery);
        }

        BuilderProperty entity = save(builderProperty);

//        LogUtil.logInfo("ClientCategoryRequestForAdd saved successfully.");
        return entity;
    }

    @Override
    public List<BuilderProperty> getAll() {
        return builderPropertyRepository.findAll();
    }
}
