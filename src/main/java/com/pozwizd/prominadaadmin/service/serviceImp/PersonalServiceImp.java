package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.entity.Feedback;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.repository.primary.PersonalRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.PersonalService;
import com.pozwizd.prominadaadmin.specification.PersonalSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Сервис для управления данными пользователей (персонала).
 * Предоставляет методы для создания, чтения, обновления и удаления информации о пользователях,
 * а также для поиска и фильтрации пользователей по различным критериям.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalServiceImp implements PersonalService {

    private final PersonalRepository personalRepository;
    private final PersonalMapper personalMapper;
    private final PasswordEncoder passwordEncoder;
    private final BranchServiceImp branchServiceImp;
    private final FileService fileService;
    private final FeedbackServiceImp feedbackServiceImp;


    /**
     * Получает список всех пользователей.
     *
     * @return Список всех пользователей в системе
     */
    @Override
    public List<Personal> findAll() {
        try {
            List<Personal> personals = personalRepository.findAll();
            log.info("Успешно получено {} пользователей", personals.size());
            return personals;
        } catch (Exception e) {
            log.error("Ошибка при получении списка пользователей", e);
            throw new OperationException("получении списка пользователей", e.getMessage());
        }
    }

    /**
     * Поиск пользователя по ID.
     *
     * @param id ID пользователя
     * @return Optional с найденным пользователем или пустой Optional
     */
    @Override
    public Optional<Personal> findById(Long id) {
        try {
            Optional<Personal> personal = personalRepository.findById(id);
            if (personal.isPresent()) {
                log.info("Пользователь с ID {} успешно найден", id);
            } else {
                log.warn("Пользователь с ID {} не найден", id);
            }

            return personal;
        } catch (Exception e) {
            log.error("Ошибка при поиске пользователя с ID {}", id, e);
            throw new OperationException("поиске пользователя с ID " + id, e.getMessage());
        }
    }

    /**
     * Поиск пользователя по email.
     *
     * @param email Email пользователя
     * @return Optional с найденным пользователем или пустой Optional
     */
    @Override
    public Optional<Personal> findByEmail(String email) {
        try {
            Optional<Personal> personal = personalRepository.findByEmail(email);
            if (personal.isPresent()) {
                log.info("Пользователь с email {} успешно найден", email);
            } else {
                log.warn("Пользователь с email {} не найден", email);
            }
            return personal;
        } catch (Exception e) {
            log.error("Ошибка при поиске пользователя с email {}", email, e);
            throw new OperationException("поиске пользователя с email " + email, e.getMessage());
        }
    }

    /**
     * Сохраняет информацию о пользователе.
     * Автоматически шифрует пароль пользователя перед сохранением.
     *
     * @param personal Данные пользователя для сохранения
     * @return Сохраненный пользователь с обновленными данными
     */
    @Transactional
    @Override
    public Personal save(Personal personal) {
        try {
            personal.setPassword(passwordEncoder.encode(personal.getPassword()));
            Personal savedPersonal = personalRepository.save(personal);
            log.info("Пользователь с ID {} успешно сохранен", savedPersonal.getId());
            return savedPersonal;
        } catch (Exception e) {
            log.error("Ошибка при сохранении пользователя", e);
            throw new OperationException("сохранении пользователя", e.getMessage());
        }
    }

    /**
     * Удаляет пользователя по ID.
     *
     * @param id ID пользователя для удаления
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            Personal personal = personalRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Personal not found"));

            personal.removeAllBranches();

            personalRepository.delete(personal);
            personalRepository.deleteById(id);
            log.info("Пользователь с ID {} успешно удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удалении пользователя с ID {}", id, e);
            throw new OperationException("удалении пользователя с ID " + id, e.getMessage());
        }
    }



    /**
     * Получает постраничный список пользователей с возможностью фильтрации.
     *
     * @param page        Номер страницы
     * @param size        Размер страницы
     * @param surname     Фамилия для фильтрации
     * @param name        Имя для фильтрации
     * @param lastName    Отчество для фильтрации
     * @param phoneNumber Телефон для фильтрации
     * @param email       Email для фильтрации
     * @param role        Роль для фильтрации
     * @return Страница с данными пользователей, соответствующих критериям фильтрации
     */
    @Override
    public Page<PersonalTableResponse> getPageablePersonal(int page, Integer size,
                                                           String surname,
                                                           String name,
                                                           String lastName,
                                                           String phoneNumber,
                                                           String email,
                                                           String role) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<PersonalTableResponse> personalPage = personalMapper.toPersonalTableResponse(personalRepository.findAll(
                    PersonalSpecification.search(surname, name, lastName, phoneNumber, email, role), pageRequest));
            log.info("Успешно получена страница с {} пользователями", personalPage.getContent().size());
            return personalPage;
        } catch (Exception e) {
            log.error("Ошибка при получении постраничного списка пользователей", e);
            throw new OperationException("получении постраничного списка пользователей", e.getMessage());
        }
    }

    /**
     * Сохраняет информацию о пользователе из PersonalRequest.
     * Обрабатывает вложенные отзывы и файлы.
     * Устанавливает двустороннюю связь с филиалами.
     *
     * @param personalRequest PersonalRequest с данными пользователя
     */
    @Transactional
    @Override
    public void saveFromRequest(PersonalRequest personalRequest) {
        try {
            log.info("Начало сохранения пользователя с данными: {}", personalRequest);
            Personal personal = personalMapper.toEntityFromPersonalRequest(personalRequest);

            if (personal.getPassword() != null) {
                personal.setPassword(passwordEncoder.encode(personal.getPassword()));
            }

            if (personalRequest.getAvatar() != null) {
                try {
                    personal.setPathAvatar(fileService.uploadFile(personalRequest.getAvatar()));
                } catch (IOException e) {
                    log.error("Ошибка при загрузке аватара", e);
                    throw new RuntimeException(e);
                }
            }

            if (personalRequest.getDocuments() != null) {
                List<DocumentFeedback> documents = new ArrayList<>();
                for (DocumentFeedbackRequest documentRequest : personalRequest.getDocuments()) {
                    DocumentFeedback document = new DocumentFeedback();
                    document.setName(documentRequest.getName());

                    if (documentRequest.getFile() != null) {
                        try {
                            document.setPathImage(fileService.uploadFile(documentRequest.getFile()));
                        } catch (IOException e) {
                            log.error("Ошибка при загрузке документа", e);
                            throw new RuntimeException(e);
                        }
                    }

                    documents.add(document);
                }

                personal.setDocumentFeedbacks(documents);
            }

            if (personalRequest.getFeedBacks() != null) {
                List<Feedback> feedbacks = new ArrayList<>();
                for (FeedbackRequest feedbackRequest : personalRequest.getFeedBacks()) {
                    Feedback feedback = new Feedback();
                    feedback.setName(feedbackRequest.getName());
                    feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
                    feedback.setDescription(feedbackRequest.getDescription());

                    feedbacks.add(feedback);
                    feedbackServiceImp.save(feedback);
                }

                personal.setFeedBacks(feedbacks);
            }

            if (personalRequest.getBranchIds() != null) {
                Set<Branch> branches = new HashSet<>();
                for (Long branchId : personalRequest.getBranchIds()) {
                    Branch branch = branchServiceImp.getBranchById(branchId);
                    branches.add(branch);
                }
                personal.setBranches(branches);
            }

            Personal savedPersonal = personalRepository.save(personal);

            if (personalRequest.getBranchIds() != null) {
                for (Long branchId : personalRequest.getBranchIds()) {
                    Branch branch = branchServiceImp.getBranchById(branchId);
                    if (!branch.getPersonals().contains(savedPersonal)) {
                        branch.getPersonals().add(savedPersonal);
                        branchServiceImp.save(branch);
                    }
                }
            };

            savedPersonal = personalRepository.save(personal);
            log.info("Пользователь с ID {} успешно сохранен", savedPersonal.getId());
        } catch (Exception e) {
            log.error("Ошибка при сохранении пользователя из PersonalRequest", e);
            throw new OperationException("сохранении пользователя из PersonalRequest", e.getMessage());
        }
    }

    @Override
    @Async
    @Transactional
    public void updatePersonal(@Valid PersonalRequest personalRequest) {
        try {
            log.info("Начало обновления пользователя с ID {}", personalRequest.getId());
            Personal oldPersonal = personalRepository.findById(personalRequest.getId()).orElseThrow();
            Personal personal = personalMapper.toUpdateEntityFromPersonalRequest(oldPersonal, personalRequest);

            if (personalRequest.getAvatar() != null) {
                try {
                    personal.setPathAvatar(fileService.uploadFile(personalRequest.getAvatar()));
                } catch (IOException e) {
                    log.error("Ошибка при загрузке аватара", e);
                    throw new RuntimeException(e);
                }
            } else {
                personal.setPathAvatar(oldPersonal.getPathAvatar());
            }

            personalRepository.save(personal);
            log.info("Пользователь с ID {} успешно обновлен", personalRequest.getId());
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя", e);
            throw new OperationException("обновлении пользователя", e.getMessage());
        }
    }

    @Async
    @Transactional
    @Override
    public CompletableFuture<Personal> updatePersonalAsync(PersonalRequest personalRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("Начало обновления пользователя с ID {}", personalRequest.getId());

                Personal oldPersonal = personalRepository.findById(personalRequest.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

                Personal personal = personalMapper.toUpdateEntityFromPersonalRequest(oldPersonal, personalRequest);

                if (personalRequest.getAvatar() != null) {
                    try {
                        personal.setPathAvatar(fileService.uploadFile(personalRequest.getAvatar()));
                    } catch (IOException e) {
                        log.error("Ошибка при загрузке аватара", e);
                        throw new RuntimeException("Ошибка при загрузке аватара", e);
                    }
                } else {
                    personal.setPathAvatar(oldPersonal.getPathAvatar());
                }

                Personal savedPersonal = personalRepository.save(personal);
                log.info("Пользователь с ID {} успешно обновлен", personalRequest.getId());

                return savedPersonal;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }
}
