package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialProperties;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.property.commercial.CommercialPropertiesMapper;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.request.CommercialPropertiesRequest;
import com.pozwizd.prominadaadmin.models.property.commercialProperty.response.CommercialPropertiesResponse;
import com.pozwizd.prominadaadmin.repository.primary.CommercialPropertiesRepository;
import com.pozwizd.prominadaadmin.service.CommercialPropertiesService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        CommercialProperties commercialProperties = commercialPropertiesRepository.findById(id)
                .orElseThrow(() -> new OperationException("получении коммерческой недвижимости",
                        "Коммерческая недвижимость с ID " + id + " не найдена", HttpStatus.NOT_FOUND));

        commercialPropertiesMapper.partialUpdate(commercialPropertiesRequest, commercialProperties);
        log.info("Коммерческая недвижимость с ID {} успешно обновлена", id);
        return commercialPropertiesMapper.toResponse(commercialProperties);
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        try {
            if (!commercialPropertiesRepository.existsById(id)) {
                throw new OperationException("удалении коммерческой недвижимости",
                        "Коммерческая недвижимость с ID " + id + " не найдена", HttpStatus.NOT_FOUND);
            }
            commercialPropertiesRepository.deleteById(id);
            log.info("Коммерческая недвижимость с ID {} успешно удалена", id);
            return true;
        } catch (OperationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ошибка при удалении коммерческой недвижимости с ID {}", id, e);
            throw new RuntimeException("Ошибка при удалении коммерческой недвижимости", e);
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
}
