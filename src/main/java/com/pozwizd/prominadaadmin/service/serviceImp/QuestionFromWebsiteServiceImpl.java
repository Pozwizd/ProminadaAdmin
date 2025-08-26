package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.QuestionFromWebsite;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.repository.primary.QuestionFromWebsiteRepository;
import com.pozwizd.prominadaadmin.service.QuestionFromWebsiteService;
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
public class QuestionFromWebsiteServiceImpl implements QuestionFromWebsiteService {

    private final QuestionFromWebsiteRepository questionFromWebsiteRepository;

    @Transactional
    @Override
    public QuestionFromWebsite create(QuestionFromWebsite questionFromWebsite) {
        try {
            log.info("Создание нового вопроса с сайта: {}", questionFromWebsite);
            QuestionFromWebsite savedQuestion = questionFromWebsiteRepository.save(questionFromWebsite);
            log.info("Вопрос с ID {} успешно создан", savedQuestion.getId());
            return savedQuestion;
        } catch (Exception e) {
            log.error("Ошибка при создании вопроса с сайта", e);
            throw new OperationException("создании вопроса с сайта", e.getMessage());
        }
    }

    @Override
    public QuestionFromWebsite getById(Long id) {
        try {
            log.info("Получение вопроса по ID: {}", id);
            QuestionFromWebsite question = questionFromWebsiteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("QuestionFromWebsite with id " + id + " not found"));
            log.info("Вопрос с ID {} успешно найден", id);
            return question;
        } catch (Exception e) {
            log.error("Ошибка при получении вопроса с ID {}", id, e);
            throw new OperationException("получении вопроса с ID " + id, e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        try {
            log.info("Удаление вопроса по ID: {}", id);
            if (!questionFromWebsiteRepository.existsById(id)) {
                log.warn("Вопрос с ID {} не найден для удаления", id);
                return false;
            }
            questionFromWebsiteRepository.deleteById(id);
            log.info("Вопрос с ID {} успешно удален", id);
            return true;
        } catch (Exception e) {
            log.error("Ошибка при удалении вопроса с ID {}", id, e);
            throw new OperationException("удалении вопроса с ID " + id, e.getMessage());
        }
    }

    @Override
    public Page<QuestionFromWebsite> getByPagination(int page, Integer size) {
        try {
            log.info("Получение постраничного списка вопросов: страница {}, размер {}", page, size);
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<QuestionFromWebsite> questionPage = questionFromWebsiteRepository.findAll(pageRequest);
            log.info("Успешно получена страница с {} вопросами", questionPage.getContent().size());
            return questionPage;
        } catch (Exception e) {
            log.error("Ошибка при получении постраничного списка вопросов", e);
            throw new OperationException("получении списка вопросов", e.getMessage());
        }
    }
}
