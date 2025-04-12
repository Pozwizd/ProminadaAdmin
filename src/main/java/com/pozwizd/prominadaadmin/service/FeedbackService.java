package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Feedback;
import com.pozwizd.prominadaadmin.mapper.FeedbackMapper;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackResponse;
import com.pozwizd.prominadaadmin.repository.FeedbackRepository;
import com.pozwizd.prominadaadmin.specification.FeedbackSpecification;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    /**
     * Получает список всех отзывов.
     * 
     * @return Список всех отзывов в системе
     */
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    /**
     * Поиск отзыва по ID.
     * 
     * @param id ID отзыва
     * @return Optional с найденным отзывом или пустой Optional
     */
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    /**
     * Сохраняет информацию об отзыве.
     * 
     * @param feedback Данные отзыва для сохранения
     * @return Сохраненный отзыв с обновленными данными
     */
    @Transactional
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    /**
     * Удаляет отзыв по ID.
     * 
     * @param id ID отзыва для удаления
     */
    @Transactional
    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
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
    public Page<FeedbackResponse> getPageableFeedback(int page, Integer size,
                                                     String name,
                                                     String phoneNumber,
                                                     String description) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return feedbackMapper.toFeedbackResponse(feedbackRepository.findAll(FeedbackSpecification.search(name,
                phoneNumber,
                description),
                pageRequest));
    }

    /**
     * Получает отзыв по ID.
     * 
     * @param id ID отзыва
     * @return Данные отзыва
     * @throws NoSuchElementException если отзыв не найден
     */
    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElseThrow();
    }
}