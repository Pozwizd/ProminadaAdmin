package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.property.secondaryProperty.SecondaryPropertyMapper;
import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.request.SecondaryPropertyRequest;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponse;
import com.pozwizd.prominadaadmin.models.property.secondaryProperty.response.SecondaryPropertyResponseForTable;
import com.pozwizd.prominadaadmin.repository.primary.SecondaryPropertyRepository;
import com.pozwizd.prominadaadmin.service.SecondaryPropertyService;
import com.pozwizd.prominadaadmin.specification.SecondaryPropertySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecondaryPropertyServiceImpl implements SecondaryPropertyService {

    private final SecondaryPropertyRepository secondaryPropertyRepository;
    private final SecondaryPropertyMapper secondaryPropertyMapper;

    @Override
    @Transactional
    public SecondaryProperty create(SecondaryProperty secondaryProperty) {
        return secondaryPropertyRepository.save(secondaryProperty);
    }

    @Override
    public SecondaryPropertyResponse create(SecondaryPropertyRequest secondaryPropertyRequest) {
        try {
            var secondaryProperty = secondaryPropertyRepository
                    .save(secondaryPropertyMapper.toEntity(secondaryPropertyRequest));
            log.info("Secondary property with ID {} successfully created", secondaryProperty.getId());
            return secondaryPropertyMapper.toResponse(secondaryProperty);
        } catch (Exception e) {
            log.error("Error occurred while creating secondary property", e);
            throw new OperationException("creating secondary property", e.getMessage());
        }
    }

    @Override
    @Transactional
    public SecondaryPropertyResponse getSecondaryPropertyById(Long id) {
        SecondaryProperty secondaryProperty = secondaryPropertyRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("получении вторичной недвижимости",
                        "Участок с ID " + id + " не найден", HttpStatus.NOT_FOUND));

        log.info("Secondary property with ID {} successfully retrieved", id);
        return secondaryPropertyMapper.toResponse(secondaryProperty);

    }

    @Override
    @Transactional
    public SecondaryPropertyResponse updateSecondaryProperty(Long id, SecondaryPropertyRequest secondaryPropertyRequest) {
        SecondaryProperty secondaryProperty = secondaryPropertyRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("updating secondary property",
                        "Secondary property with ID " + id + " not found", HttpStatus.NOT_FOUND));

        secondaryPropertyMapper.updateSecondaryPropertyFromRequest(secondaryPropertyRequest, secondaryProperty);
        log.info("Secondary property with ID {} successfully updated", id);
        return secondaryPropertyMapper.toResponse(secondaryPropertyRepository.save(secondaryProperty));
    }

    @Override
    @Transactional
    public Boolean deleteSecondaryPropertyById(Long id) {
        SecondaryProperty entity = secondaryPropertyRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("deleting secondary property",
                        "Secondary property with ID " + id + " not found", HttpStatus.NOT_FOUND));

        try {
            Hibernate.initialize(entity.getSecondaryPropertyMain());
            Hibernate.initialize(entity.getSecondaryPropertyFiles());
            Hibernate.initialize(entity.getSecondaryPropertyGalleryImages());

            entity.getSecondaryPropertyFiles().clear();
            entity.getSecondaryPropertyGalleryImages().clear();

            if (entity.getSecondaryPropertyMain() != null) {
                entity.setSecondaryPropertyMain(null);
            }

            secondaryPropertyRepository.saveAndFlush(entity);
            secondaryPropertyRepository.delete(entity);
            secondaryPropertyRepository.flush();

            log.info("Secondary property with ID {} successfully deleted", id);
            return true;
        } catch (Exception e) {
            log.error("Error deleting residential land with ID {}: {}", id, e.getMessage(), e);
            throw new OperationException("deleting secondary property",
                    "Failed to delete: " + e.getMessage());
        }

    }


    @Override
    public Page<SecondaryPropertyResponseForTable> getSecondaryPropertyResponseByPagination(PropertiesFilter filter) {
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        return secondaryPropertyMapper.toResponseForTablePage(
                secondaryPropertyRepository.findAll(
                        SecondaryPropertySpecification.search(filter),
                        pageRequest
                )
        );
    }

}
