package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.mapper.BuilderPropertyMapper;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDtoForTable;
import com.pozwizd.prominadaadmin.repository.BuilderPropertyRepository;
import com.pozwizd.prominadaadmin.service.BuilderPropertyService;
import com.pozwizd.prominadaadmin.service.ImageService;
import com.pozwizd.prominadaadmin.specification.BuilderPropertySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuilderPropertyServiceImp implements BuilderPropertyService {
    private final BuilderPropertyRepository builderPropertyRepository;
    private final BuilderPropertyMapper builderPropertyMapper;
    private final ImageService imageService;
    @Value("${upload.path}")
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
    public BuilderProperty save(BuilderProperty builderProperty) {
        return builderPropertyRepository.save(builderProperty);
    }

    @SneakyThrows
    @Override
    public BuilderProperty save(BuilderPropertyDto dto) {
//        LogUtil.logInfo("Starting to save ClientCategoryRequestForAdd: " + dto);

        if (dto.getChessPlanFile() != null) {
            String generatedPath = contextPath + "/builder/" + imageService.generateFileName(dto.getChessPlanFile());
            dto.setPathToChessPlanFile(generatedPath);
//            LogUtil.logInfo("Generated image path for category: " + generatedPath);
        }

        if (dto.getId() != null) {
//            LogUtil.logInfo("ClientCategoryId provided: " + dto.getClientsCategoryId());
            BuilderProperty builderProperty = getById(dto.getId());
//            LogUtil.logInfo("Found ClientCategory by ID: " + builderProperty);

            if (dto.getPathToChessPlanFile().isEmpty()) {
//                LogUtil.logInfo("Deleting old image for category with ID: " + dto.getClientsCategoryId());
                imageService.deleteByPath(builderProperty.getPathToChessPlanFile());
            }
            if (dto.getPathToMortgageConditionsFile().isEmpty()) {
//                LogUtil.logInfo("Deleting old image for category with ID: " + dto.getClientsCategoryId());
                imageService.deleteByPath(builderProperty.getPathToMortgageConditionsFile());
            }
            if (dto.getPathToPriceFile().isEmpty()) {
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

        BuilderProperty entity = save(builderPropertyMapper.toEntityFromRequest(dto));
//        LogUtil.logInfo("ClientCategoryRequestForAdd saved successfully.");
        return entity;
    }
}
