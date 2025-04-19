package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.mapper.FeedbackMapper;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import com.pozwizd.prominadaadmin.specification.PersonalSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Сервис для управления данными пользователей (персонала).
 * Предоставляет методы для создания, чтения, обновления и удаления информации о пользователях,
 * а также для поиска и фильтрации пользователей по различным критериям.
 */
@Service
@RequiredArgsConstructor
public class PersonalService {

    private final PersonalRepository personalRepository;
    private final PersonalMapper personalMapper;
    private final PasswordEncoder passwordEncoder;
    private final BranchService branchService;
    private final DocumentFeedbackService documentFeedbackService;
    private final FileService fileService;
    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    /**
     * Получает список всех пользователей.
     * 
     * @return Список всех пользователей в системе
     */
    public List<Personal> findAll() {
        return personalRepository.findAll();
    }

    /**
     * Поиск пользователя по ID.
     * 
     * @param id ID пользователя
     * @return Optional с найденным пользователем или пустой Optional
     */
    public Optional<Personal> findById(Long id) {
        return personalRepository.findById(id);
    }

    /**
     * Поиск пользователя по email.
     * 
     * @param email Email пользователя
     * @return Optional с найденным пользователем или пустой Optional
     */
    public Optional<Personal> findByEmail(String email) {
        return personalRepository.findByEmail(email);
    }

    /**
     * Сохраняет информацию о пользователе.
     * Автоматически шифрует пароль пользователя перед сохранением.
     * 
     * @param personal Данные пользователя для сохранения
     * @return Сохраненный пользователь с обновленными данными
     */
    @Transactional
    public Personal save(Personal personal) {
        personal.setPassword(passwordEncoder.encode(personal.getPassword()));
        return personalRepository.save(personal);
    }

    /**
     * Удаляет пользователя по ID.
     * 
     * @param id ID пользователя для удаления
     */
    @Transactional
    public void deleteById(Long id) {
        personalRepository.deleteById(id);
    }

    /**
     * Получает постраничный список пользователей с возможностью фильтрации.
     * 
     * @param page Номер страницы
     * @param size Размер страницы
     * @param surname Фамилия для фильтрации
     * @param name Имя для фильтрации
     * @param lastName Отчество для фильтрации
     * @param phoneNumber Телефон для фильтрации
     * @param email Email для фильтрации
     * @param role Роль для фильтрации
     * @return Страница с данными пользователей, соответствующих критериям фильтрации
     */
    public Page<PersonalTableResponse> getPageablePersonal(int page, Integer size,
                                                           String surname,
                                                           String name,
                                                           String lastName,
                                                           String phoneNumber,
                                                           String email,
                                                           String role) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return personalMapper.toPersonalTableResponse(personalRepository.findAll(PersonalSpecification.search(surname,
                name,
                lastName,
                phoneNumber,
                email,
                role),
                pageRequest));

    }

    /**
     * Удаляет пользователя по ID.
     * 
     * @param id ID пользователя для удаления
     */
    public void deletePersonal(Long id) {
        personalRepository.deleteById(id);
    }

    /**
     * Получает пользователя по ID.
     * 
     * @param id ID пользователя
     * @return Данные пользователя
     * @throws NoSuchElementException если пользователь не найден
     */
    public Personal getPersonalById(Long id) {
        return personalRepository.findById(id).orElseThrow();
    }

    /**
     * Сохраняет информацию о пользователе из PersonalRequest.
     * Обрабатывает вложенные отзывы и файлы.
     *
     * @param request PersonalRequest с данными пользователя
     */
    @Transactional
    public void saveFromRequest(PersonalRequest request) {
        Personal personal = personalMapper.toEntityFromPersonalRequest(request, documentFeedbackService, branchService, fileService, feedbackService);

        personalRepository.save(personal);
    }

    public void updatePersonal(@Valid PersonalRequest personalRequest) {
        Personal oldPersonal = personalRepository.findById(personalRequest.getId()).orElseThrow();
        Personal personal = personalMapper.toEntityFromPersonalRequest(oldPersonal,
                personalRequest,
                documentFeedbackService,
                branchService,
                fileService,
                feedbackService);
        personalRepository.save(personal);
    }
}