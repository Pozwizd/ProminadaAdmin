package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Page;
import com.pozwizd.prominadaadmin.models.page.PageResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PageMapper {
    Page toEntity(PageResponse pageResponse);

    PageResponse toDto(Page page);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Page partialUpdate(PageResponse pageResponse, @MappingTarget Page page);
}