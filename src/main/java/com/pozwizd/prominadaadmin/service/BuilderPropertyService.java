package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.models.filter.BuilderPropertyFilter;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyResponseForTable;
import com.pozwizd.prominadaadmin.models.property.builderProperty.request.BuilderPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BuilderPropertyService {
    
    BuilderProperty create(BuilderProperty builderProperty);

    BuilderProperty readById(Long id);

    BuilderPropertyResponse createFromRequest(BuilderPropertyRequest builderPropertyRequest);

    BuilderPropertyResponse readResponseById(Long id);

    BuilderPropertyResponse update(BuilderPropertyRequest builderPropertyRequest);

    Boolean deleteById(Long id);


//    Page<BuilderPropertyDtoForTable> getPageableBuilders(int page, Integer size, BuilderPropertyDtoForTable builderPropertyDto);
//
//    void deleteById(Long id);
//
//    BuilderProperty getById(Long id);
//
//    BuilderPropertyDto getByIdInDto(Long id);
//
    BuilderProperty save(BuilderProperty builderProperty);
//
//    BuilderProperty save(BuilderPropertyDto dto);
//
    List<BuilderProperty> getAll();

    Page<BuilderPropertyResponseForTable> getBuilderPropertiesByPagination(BuilderPropertyFilter filter);
}
