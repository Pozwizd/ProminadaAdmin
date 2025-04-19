package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import org.springframework.data.domain.Page;

public interface BuilderPropertyService {
    Page<BuilderPropertyDto> getPageableBuilders(int page, Integer size, BuilderPropertyDto builderPropertyDto);

    void deleteById(Long id);

    BuilderProperty save(BuilderProperty builderProperty);
}
