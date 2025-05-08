package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDtoForTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BuilderPropertyService {
    Page<BuilderPropertyDtoForTable> getPageableBuilders(int page, Integer size, BuilderPropertyDtoForTable builderPropertyDto);

    void deleteById(Long id);

    BuilderProperty getById(Long id);

    BuilderPropertyDto getByIdInDto(Long id);

    BuilderProperty save(BuilderProperty builderProperty);

    BuilderProperty save(BuilderPropertyDto dto);

    List<BuilderProperty> getAll();
}
