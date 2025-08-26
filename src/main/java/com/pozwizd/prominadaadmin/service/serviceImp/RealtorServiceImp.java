package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.models.filter.RealtorFilter;
import com.pozwizd.prominadaadmin.mapper.RealtorMapper;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorResponse;
import com.pozwizd.prominadaadmin.repository.primary.RealtorRepository;
import com.pozwizd.prominadaadmin.service.RealtorService;
import com.pozwizd.prominadaadmin.specification.RealtorSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RealtorServiceImp implements RealtorService {

    private final RealtorRepository realtorRepository;
    private final RealtorMapper realtorMapper;

    @Named("getRealtorByCode")
    @Override
    public Realtor getByCode(String code) {
        return realtorRepository.findByCode(code).orElse(null);
    }

    @Override
    public Page<RealtorResponse> getPageableRealtors(RealtorFilter filter) {
        try {
            PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
            Specification<Realtor> spec = RealtorSpecification.filterBy(filter);
            Page<Realtor> realtorPage = realtorRepository.findAll(spec, pageRequest);
            Page<RealtorResponse> responsePage = realtorPage.map(realtorMapper::toRealtorResponse);
            log.info("Успешно получена страница с {} риелторами", responsePage.getContent().size());
            return responsePage;
        } catch (Exception e) {
            log.error("Ошибка при получении постраничного списка риелторов", e);
            throw new OperationException("получении списка риелторов", e.getMessage());
        }
    }

    @Transactional
    @Override
    public Realtor create(Realtor realtor) {
        try {
            log.info("Создание нового риелтора: {}", realtor);
            Realtor savedRealtor = realtorRepository.save(realtor);
            log.info("Риелтор с ID {} успешно создан", savedRealtor.getId());
            return savedRealtor;
        } catch (Exception e) {
            log.error("Ошибка при создании риелтора", e);
            throw new OperationException("создании риелтора", e.getMessage());
        }
    }

    @Transactional
    @Override
    public RealtorResponse create(RealtorRequest realtorRequest) {
        try {
            log.info("Асинхронное создание риелтора из запроса: {}", realtorRequest);
            Realtor realtorToSave = realtorMapper.toEntity(realtorRequest);
            realtorRepository.save(realtorToSave);
            log.info("Риелтор успешно создан из запроса");
            return realtorMapper.toRealtorResponse(realtorToSave);
        } catch (Exception e) {
            log.error("Ошибка при асинхронном создании риелтора", e);
            throw new CompletionException(new OperationException("создании риелтора из запроса", e.getMessage()));
        }
    }
    @Override
    public Realtor readById(Long id) {
        try {
            log.info("Получение риелтора по ID: {}", id);
            Realtor realtor = realtorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Realtor with id " + id + " not found"));
            log.info("Риелтор с ID {} успешно найден", id);
            return realtor;
        } catch (Exception e) {
            log.error("Ошибка при получении риелтора с ID {}", id, e);
            throw new OperationException("получении риелтора с ID " + id, e.getMessage());
        }
    }

    @Override
    public RealtorResponse readResponseById(Long id) {
        try {
            log.info("Получение RealtorResponse по ID: {}", id);
            Realtor realtor = readById(id);
            RealtorResponse response = realtorMapper.toRealtorResponse(realtor);
            log.info("RealtorResponse для ID {} успешно получен", id);
            return response;
        } catch (Exception e) {
            log.error("Ошибка при получении RealtorResponse с ID {}", id, e);
            throw new OperationException("получении RealtorResponse с ID " + id, e.getMessage());
        }
    }

    @Override
    public List<Realtor> findAll() {
        return realtorRepository.findAll();
    }

    @Transactional
    @Override
    public Realtor update(Realtor realtor) {
        try {
            if (realtor.getId() == null) {
                throw new IllegalArgumentException("Realtor ID cannot be null for an update operation");
            }

            log.info("Обновление риелтора: {}", realtor);
            Realtor updatedRealtor = realtorRepository.save(realtor);
            log.info("Риелтор с ID {} успешно обновлен", updatedRealtor.getId());
            return updatedRealtor;
        } catch (Exception e) {
            log.error("Ошибка при обновлении риелтора", e);
            throw new OperationException("обновлении риелтора", e.getMessage());
        }
    }

    @Transactional
    @Override
    public RealtorResponse update(RealtorRequest realtorRequest, Long id) {
        try {
            log.info("Асинхронное обновление риелтора с ID {} из запроса: {}", id, realtorRequest);
            if (!realtorRepository.existsById(id)) {
                log.warn("Риелтор с ID {} не найден для обновления", id);
                throw new EntityNotFoundException("Realtor with id " + id + " not found");
            }
            if (realtorRequest.getId() != null && !realtorRequest.getId().equals(id)) {
                log.warn("ID в пути и в теле запроса не совпадают");
                throw new IllegalArgumentException("ID в пути и в теле запроса не совпадают");
            }
            Realtor realtorToUpdate = readById(id);
            realtorMapper.partialUpdate(realtorRequest, realtorToUpdate);

            realtorRepository.save(realtorToUpdate);
            log.info("Риелтор с ID {} успешно обновлен из запроса", id);
            return realtorMapper.toRealtorResponse(realtorRepository.save(realtorToUpdate));
        } catch (Exception e) {
            log.error("Ошибка при асинхронном обновлении риелтора с ID {}", id, e);
            throw new CompletionException(new OperationException("обновлении риелтора с ID " + id, e.getMessage()));
        }
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        try {
            log.info("Удаление риелтора по ID: {}", id);
            if (!realtorRepository.existsById(id)) {
                log.warn("Риелтор с ID {} не найден для удаления", id);
                return false;
            }
            realtorRepository.deleteById(id);
            log.info("Риелтор с ID {} успешно удален", id);
            return true;
        } catch (Exception e) {
            log.error("Ошибка при удалении риелтора с ID {}", id, e);
            throw new OperationException("удалении риелтора с ID " + id, e.getMessage());
        }
    }
}
