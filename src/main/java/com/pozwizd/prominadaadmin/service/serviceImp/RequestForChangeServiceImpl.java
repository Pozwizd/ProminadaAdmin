package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.RequestForChange;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.repository.primary.RequestForChangeRepository;
import com.pozwizd.prominadaadmin.service.RequestForChangeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestForChangeServiceImpl implements RequestForChangeService {

    private final RequestForChangeRepository requestForChangeRepository;

    @Transactional
    @Override
    public RequestForChange create(RequestForChange requestForChange) {
        try {
            log.info("Создание нового запроса на изменение: {}", requestForChange);
            RequestForChange savedRequest = requestForChangeRepository.save(requestForChange);
            log.info("Запрос на изменение с ID {} успешно создан", savedRequest.getId());
            return savedRequest;
        } catch (Exception e) {
            log.error("Ошибка при создании запроса на изменение", e);
            throw new OperationException("создании запроса на изменение", e.getMessage());
        }
    }

    @Override
    public RequestForChange getById(Long id) {
        try {
            log.info("Получение запроса на изменение по ID: {}", id);
            RequestForChange request = requestForChangeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("RequestForChange with id " + id + " not found"));
            log.info("Запрос на изменение с ID {} успешно найден", id);
            return request;
        } catch (Exception e) {
            log.error("Ошибка при получении запроса на изменение с ID {}", id, e);
            throw new OperationException("получении запроса на изменение с ID " + id, e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        try {
            log.info("Удаление запроса на изменение по ID: {}", id);
            if (!requestForChangeRepository.existsById(id)) {
                log.warn("Запрос на изменение с ID {} не найден для удаления", id);
                return false;
            }
            requestForChangeRepository.deleteById(id);
            log.info("Запрос на изменение с ID {} успешно удален", id);
            return true;
        } catch (Exception e) {
            log.error("Ошибка при удалении запроса на изменение с ID {}", id, e);
            throw new OperationException("удалении запроса на изменение с ID " + id, e.getMessage());
        }
    }

    @Override
    public Page<RequestForChange> getByPagination(int page, Integer size) {
        try {
            log.info("Получение постраничного списка запросов на изменение: страница {}, размер {}", page, size);
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<RequestForChange> requestPage = requestForChangeRepository.findAll(pageRequest);
            log.info("Успешно получена страница с {} запросами на изменение", requestPage.getContent().size());
            return requestPage;
        } catch (Exception e) {
            log.error("Ошибка при получении постраничного списка запросов на изменение", e);
            throw new OperationException("получении списка запросов на изменение", e.getMessage());
        }
    }
}
