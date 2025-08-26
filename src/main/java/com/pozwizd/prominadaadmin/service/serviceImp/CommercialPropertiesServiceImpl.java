package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.property.commercial.CommercialPropertiesMapper;
import com.pozwizd.prominadaadmin.models.filter.PropertiesFilter;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponseForTable;
import com.pozwizd.prominadaadmin.models.property.investor.response.InvestorPropertyResponseForTable;
import com.pozwizd.prominadaadmin.repository.primary.CommercialPropertiesRepository;
import com.pozwizd.prominadaadmin.service.CommercialPropertiesService;
import com.pozwizd.prominadaadmin.specification.CommercialPropertiesSpecification;
import com.pozwizd.prominadaadmin.specification.InvestorPropertySpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommercialPropertiesServiceImpl implements CommercialPropertiesService {

    private final CommercialPropertiesRepository commercialPropertiesRepository;
    private final CommercialPropertiesMapper commercialPropertiesMapper;

    @Override
    public CommercialProperties create(CommercialProperties commercialProperties) {
        try {
            CommercialProperties savedProperties = commercialPropertiesRepository.save(commercialProperties);
            log.info("Коммерческая недвижимость с ID {} успешно создана", savedProperties.getId());
            return savedProperties;
        } catch (Exception e) {
            log.error("Ошибка при создании коммерческой недвижимости", e);
            throw new RuntimeException("Ошибка при создании коммерческой недвижимости", e);
        }
    }

    @Override
    public void delete(CommercialProperties commercialProperties) {
        try {
            commercialPropertiesRepository.delete(commercialProperties);
            log.info("Коммерческая недвижимость с ID {} успешно удалена", commercialProperties.getId());
        } catch (Exception e) {
            log.error("Ошибка при удалении коммерческой недвижимости с ID {}", commercialProperties.getId(), e);
            throw new RuntimeException("Ошибка при удалении коммерческой недвижимости", e);
        }
    }

    @Override
    @Transactional
    public CommercialPropertiesResponse create(@Valid CommercialPropertiesRequest commercialPropertiesRequest) {
        try {
            CommercialProperties commercialProperties =
                    commercialPropertiesRepository.save(
                            commercialPropertiesMapper.toEntity(commercialPropertiesRequest));
            log.info("Коммерческая недвижимость с ID {} успешно создана", commercialProperties.getId());
            return commercialPropertiesMapper.toResponse(commercialProperties);
        } catch (Exception e) {
            log.error("Ошибка при создании коммерческой недвижимости", e);
            throw new RuntimeException("Ошибка при создании коммерческой недвижимости", e);
        }
    }

    @Override
    @Transactional
    public CommercialPropertiesResponse readById(Long id) {
        CommercialProperties commercialProperties = commercialPropertiesRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("получении коммерческой недвижимости",
                        "Коммерческая недвижимость с ID " + id + " не найдена", HttpStatus.NOT_FOUND));
        log.info("Коммерческая недвижимость с ID {} успешно получена", id);
        return commercialPropertiesMapper.toResponse(commercialProperties);
    }

    @Override
    @Transactional
    public CommercialPropertiesResponse update(Long id, @Valid CommercialPropertiesRequest commercialPropertiesRequest) {
        CommercialProperties commercialProperties = commercialPropertiesRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("получении коммерческой недвижимости",
                        "Коммерческая недвижимость с ID " + id + " не найдена", HttpStatus.NOT_FOUND));

        commercialPropertiesMapper.partialUpdate(commercialPropertiesRequest, commercialProperties);
        log.info("Коммерческая недвижимость с ID {} успешно обновлена", id);
        return commercialPropertiesMapper.toResponse(commercialPropertiesRepository.save(commercialProperties));
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        CommercialProperties entity = commercialPropertiesRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new OperationException("deleting commercial property",
                        "Commercial property with ID " + id + " not found"));

        try {
            Hibernate.initialize(entity.getCommercialPropertiesMain());
            Hibernate.initialize(entity.getCommercialPropertiesGalleryImages());
            Hibernate.initialize(entity.getCommercialPropertiesFiles());

            entity.getCommercialPropertiesFiles().clear();
            entity.getCommercialPropertiesGalleryImages().clear();

            if (entity.getCommercialPropertiesMain() != null) {
                entity.setCommercialPropertiesMain(null);
            }

            commercialPropertiesRepository.saveAndFlush(entity);
            commercialPropertiesRepository.delete(entity);
            commercialPropertiesRepository.flush();

            log.info("Commercial property with ID {} successfully deleted", id);
            return true;
        } catch (Exception e) {
            log.error("Error deleting commercial land with ID {}: {}", id, e.getMessage(), e);
            throw new OperationException("deleting commercial property",
                    "Failed to delete: " + e.getMessage());
        }
    }


    @Override
    public Page<CommercialPropertiesResponse> getPageCommercialProperties(int page, Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<CommercialProperties> commercialPropertiesPage = commercialPropertiesRepository.findAll(pageRequest);
            return commercialPropertiesMapper.toResponsePage(commercialPropertiesPage);
        } catch (Exception e) {
            log.error("Ошибка при получении страницы коммерческой недвижимости", e);
            throw new RuntimeException("Ошибка при получении страницы коммерческой недвижимости", e);
        }
    }



    @Override
    public Page<CommercialPropertiesResponseForTable> getCommercialPropertiesByPagination(PropertiesFilter filter) {
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        return commercialPropertiesMapper.toResponseForTablePage(
                commercialPropertiesRepository.findAll(
                        CommercialPropertiesSpecification.search(filter),
                        pageRequest
                )
        );
    }
}
