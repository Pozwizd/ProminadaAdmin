package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.mapper.BuilderPropertyMapper;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import com.pozwizd.prominadaadmin.repository.BuilderPropertyRepository;
import com.pozwizd.prominadaadmin.service.BuilderPropertyService;
import com.pozwizd.prominadaadmin.specification.BuilderPropertySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuilderPropertyServiceImp implements BuilderPropertyService {
    private final BuilderPropertyRepository builderPropertyRepository;
    private final BuilderPropertyMapper builderPropertyMapper;

    @Override
    public Page<BuilderPropertyDto> getPageableBuilders(int page, Integer size, BuilderPropertyDto builderPropertyDto) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return builderPropertyMapper.toDto(builderPropertyRepository.findAll(BuilderPropertySpecification.search(builderPropertyDto),
                pageRequest));
    }

    @Override
    public void deleteById(Long id) {
        builderPropertyRepository.deleteById(id);
    }

    @Override
    public BuilderProperty save(BuilderProperty builderProperty) {
        return builderPropertyRepository.save(builderProperty);
    }
}
