package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.Feedback;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.FeedbackMapper;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackResponse;
import com.pozwizd.prominadaadmin.repository.primary.FeedbackRepository;
import com.pozwizd.prominadaadmin.service.FeedbackService;
import com.pozwizd.prominadaadmin.specification.FeedbackSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Сервис для управления отзывами (обратной связью).
 * Предоставляет методы для создания, чтения, обновления и удаления информации об отзывах,
 * а также для поиска и фильтрации отзывов по различным критериям.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackServiceImp implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    /**
     * Получает список всех отзывов.
     *
     * @return Список всех отзывов в системе
     */
    @Override
    public List<Feedback> findAll() {
        try {
            List<Feedback> feedbacks = feedbackRepository.findAll();
            log.info("Успешно получено {} отзывов", feedbacks.size()); // Логирование успеха
            return feedbacks;
        } catch (Exception e) {
            log.error("Ошибка при получении всех отзывов", e);
            throw new OperationException("получении всех отзывов", e.getMessage());
        }
    }

    /**
     * Поиск отзыва по ID.
     *
     * @param id ID отзыва
     * @return Optional с найденным отзывом или пустой Optional
     */
    @Override
    public Optional<Feedback> findById(Long id) {
        try {
            Optional<Feedback> feedback = feedbackRepository.findById(id);
            if (feedback.isPresent()) {
                log.info("Отзыв с ID {} найден", id);
            } else {
                log.warn("Отзыв с ID {} не найден", id);
            }
            return feedback;
        } catch (Exception e) {
            log.error("Ошибка при поиске отзыва по ID", e);
            throw new OperationException("поиске отзыва по ID", e.getMessage());
        }
    }

    /**
     * Сохраняет информацию об отзыве.
     *
     * @param feedback Данные отзыва для сохранения
     * @return Сохраненный отзыв с обновленными данными
     */
    @Transactional
    @Override
    public Feedback save(Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackRepository.save(feedback);
            log.info("Отзыв успешно сохранен с ID {}", savedFeedback.getId());
            return savedFeedback;
        } catch (Exception e) {
            log.error("Ошибка при сохранении отзыва", e);
            throw new OperationException("сохранении отзыва", e.getMessage());
        }
    }

    /**
     * Удаляет отзыв по ID.
     *
     * @param id ID отзыва для удаления
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            feedbackRepository.deleteById(id);
            log.info("Отзыв с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удалении отзыва по ID", e);
            throw new OperationException("удалении отзыва", e.getMessage());
        }
    }

    /**
     * Получает постраничный список отзывов с возможностью фильтрации.
     *
     * @param page Номер страницы
     * @param size Размер страницы
     * @param name Имя клиента для фильтрации
     * @param phoneNumber Телефон для фильтрации
     * @param description Описание для фильтрации
     * @return Страница с данными отзывов, соответствующих критериям фильтрации
     */
    @Override
    public Page<FeedbackResponse> getPageableFeedback(int page, Integer size,
                                                      String name,
                                                      String phoneNumber,
                                                      String description) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<FeedbackResponse> pageResponse = feedbackMapper.toFeedbackResponse(feedbackRepository.findAll(
                    FeedbackSpecification.search(name, phoneNumber, description), pageRequest
            ));
            log.info("Получена страница с {} отзывами", pageResponse.getContent().size());
            return pageResponse;
        } catch (Exception e) {
            log.error("Ошибка при получении постраничного списка отзывов", e);
            throw new OperationException("получении постраничного списка отзывов", e.getMessage());
        }
    }

    /**
     * Получает отзыв по ID.
     *
     * @param id ID отзыва
     * @return Данные отзыва
     * @throws NoSuchElementException если отзыв не найден
     */
    @Override
    public Feedback getFeedbackById(Long id) {
        try {
            Feedback feedback = feedbackRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Отзыв с ID " + id + " не найден"));
            log.info("Отзыв с ID {} успешно получен", id);
            return feedback;
        } catch (NoSuchElementException e) {
            log.error("Отзыв с ID {} не найден", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Ошибка при получении отзыва по ID", e);
            throw new OperationException("получении отзыва", e.getMessage());
        }
    }
}
