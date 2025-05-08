package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import com.pozwizd.prominadaadmin.entity.Feedback;
import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.mapper.FeedbackMapper;
import com.pozwizd.prominadaadmin.mapper.PersonalMapper;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalRequest;
import com.pozwizd.prominadaadmin.models.personal.PersonalTableResponse;
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.PersonalService;
import com.pozwizd.prominadaadmin.specification.PersonalSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для управления данными пользователей (персонала).
 * Предоставляет методы для создания, чтения, обновления и удаления информации о пользователях,
 * а также для поиска и фильтрации пользователей по различным критериям.
 */
@Service
@RequiredArgsConstructor
public class PersonalServiceImp implements PersonalService {

    private final PersonalRepository personalRepository;
    private final PersonalMapper personalMapper;
    private final PasswordEncoder passwordEncoder;
    private final BranchServiceImp branchServiceImp;
    private final DocumentFeedbackServiceImp documentFeedbackServiceImp;
    private final FileService fileService;
    private final FeedbackServiceImp feedbackServiceImp;
    private final FeedbackMapper feedbackMapper;

    /**
     * Получает список всех пользователей.
     *
     * @return Список всех пользователей в системе
     */
    @Override
    public List<Personal> findAll() {
        return personalRepository.findAll();
    }

    /**
     * Поиск пользователя по ID.
     *
     * @param id ID пользователя
     * @return Optional с найденным пользователем или пустой Optional
     */
    @Override
    public Optional<Personal> findById(Long id) {
        return personalRepository.findById(id);
    }

    /**
     * Поиск пользователя по email.
     *
     * @param email Email пользователя
     * @return Optional с найденным пользователем или пустой Optional
     */
    @Override
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
    @Override
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
    @Override
    public void deleteById(Long id) {
        personalRepository.deleteById(id);
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
    @Override
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
    @Override
    public Personal getPersonalById(Long id) {
        return personalRepository.findById(id).orElseThrow();
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
        Personal personal = personalMapper.toEntityFromPersonalRequest(personalRequest);


        if (personal.getPassword() != null) {
            personal.setPassword(passwordEncoder.encode(personal.getPassword()));
        }

        if (personalRequest.getAvatar() != null) {
            try {
                personal.setPathAvatar(fileService.uploadFile(personalRequest.getAvatar()));
            } catch (IOException e) {
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
                        document.setPath(fileService.uploadFile(documentRequest.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                document.setPersonal(personal);
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
                feedback.setPersonal(personal);
                feedbacks.add(feedback);
                feedbackServiceImp.save(feedback);
            }

            personal.setFeedBacks(feedbacks);
        }
        Personal savedPersonal = personalRepository.save(personal);
        if (personalRequest.getBranchIds() != null) {
            ArrayList<Branch> branches = new ArrayList<>();
            for (Long l : personalRequest.getBranchIds()) {
                Branch branchById = branchServiceImp.getBranchById(l);
                branches.add(branchById);
                if (branchById.getPersonals() == null) {
                    branchById.addPersonal(savedPersonal);
                } else {
                    List<Personal> personals = new ArrayList<>();
                    personals.add(savedPersonal);
                    branchById.setPersonals(personals);
                }

            }
            personal.setBranches(
                    branches
            );
        }

        personalRepository.save(personal);
    }

    @Transactional
    @Override
    public void updatePersonal(@Valid PersonalRequest personalRequest) {
        Personal oldPersonal = personalRepository.findById(personalRequest.getId()).orElseThrow();

        Personal personal = personalMapper.toUpdateEntityFromPersonalRequest(oldPersonal,
                personalRequest);

        if (personalRequest.getAvatar() != null) {
            try {
                personal.setPathAvatar(fileService.uploadFile(personalRequest.getAvatar()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            personal.setPathAvatar(oldPersonal.getPathAvatar());
        }

        List<DocumentFeedback> updatedDocuments = new ArrayList<>();
        if (personalRequest.getDocuments() != null) {
            Map<Long, DocumentFeedbackRequest> requestDocumentsMap = personalRequest.getDocuments().stream()
                    .filter(doc -> doc.getId() != null)
                    .collect(Collectors.toMap(DocumentFeedbackRequest::getId, doc -> doc));

            if (oldPersonal.getDocumentFeedbacks() != null) {
                for (DocumentFeedback oldDoc : oldPersonal.getDocumentFeedbacks()) {
                    if (requestDocumentsMap.containsKey(oldDoc.getId())) {
                        DocumentFeedbackRequest req = requestDocumentsMap.get(oldDoc.getId());

                        if (req.getName() != null) {
                            oldDoc.setName(req.getName());
                        }

                        if (req.getFile() != null) {
                            try {
                                oldDoc.setPath(fileService.uploadFile(req.getFile()));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        oldDoc.setPersonal(personal);
                        updatedDocuments.add(oldDoc);
                    } else {
                        try {
                            fileService.deleteFile(oldDoc.getPath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        documentFeedbackServiceImp.deleteById(oldDoc.getId());
                    }
                }
            }

            // Добавляем новые документы
            for (DocumentFeedbackRequest documentRequest : personalRequest.getDocuments()) {
                if (documentRequest.getId() == null) {
                    DocumentFeedback newDocument = new DocumentFeedback();
                    newDocument.setName(documentRequest.getName());

                    if (documentRequest.getFile() != null) {
                        try {
                            newDocument.setPath(fileService.uploadFile(documentRequest.getFile()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    newDocument.setPersonal(personal);
                    updatedDocuments.add(newDocument);
                }
            }

            personal.setDocumentFeedbacks(updatedDocuments);
        }

        if (personalRequest.getFeedBacks() != null) {
            List<Feedback> updatedFeedbacks = new ArrayList<>();

            Map<Long, FeedbackRequest> requestFeedbacksMap = personalRequest.getFeedBacks().stream()
                    .filter(feedback -> feedback.getId() != null)
                    .collect(Collectors.toMap(FeedbackRequest::getId, feedback -> feedback));

            if (oldPersonal.getFeedBacks() != null) {
                for (Feedback oldFeedback : oldPersonal.getFeedBacks()) {
                    if (!requestFeedbacksMap.containsKey(oldFeedback.getId())) {
                        feedbackServiceImp.deleteById(oldFeedback.getId());
                    }
                }
            }

            for (FeedbackRequest feedbackRequest : personalRequest.getFeedBacks()) {
                Feedback feedback = new Feedback();
                if (feedbackRequest.getId() != null) {
                    feedback.setId(feedbackRequest.getId());
                }
                feedback.setName(feedbackRequest.getName());
                feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
                feedback.setDescription(feedbackRequest.getDescription());
                feedback.setPersonal(personal);
                updatedFeedbacks.add(feedback);
                feedbackServiceImp.save(feedback);
            }

            personal.setFeedBacks(updatedFeedbacks);
        }

        List<Branch> newBranches = personalRequest.getBranchIds().stream()
                .map(branchServiceImp::getBranchById)
                .collect(Collectors.toCollection(ArrayList::new));

        if (oldPersonal.getBranches() != null) {
            for (Branch oldBranch : oldPersonal.getBranches()) {
                if (!newBranches.contains(oldBranch)) {
                    oldBranch.getPersonals().remove(oldPersonal);
                }
            }
        }

        personal.setBranches(newBranches);

        for (Branch branch : newBranches) {
            if (branch.getPersonals() == null) {
                branch.setPersonals(new ArrayList<>());
            }
            if (!branch.getPersonals().contains(personal)) {
                branch.getPersonals().add(personal);
            }
        }

        personalRepository.save(personal);
    }
}