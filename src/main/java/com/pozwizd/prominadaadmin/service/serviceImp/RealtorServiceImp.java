package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.*;
import com.pozwizd.prominadaadmin.mapper.FeedbackMapper;
import com.pozwizd.prominadaadmin.mapper.RealtorMapper;
import com.pozwizd.prominadaadmin.models.PhoneNumberResponse;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorTableResponse;
import com.pozwizd.prominadaadmin.repository.RealtorRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.RealtorService;
import com.pozwizd.prominadaadmin.specification.RealtorSpecification;
import com.pozwizd.prominadaadmin.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RealtorServiceImp implements RealtorService {
    private final RealtorRepository realtorRepository;
    private final RealtorMapper realtorMapper;
    private final PasswordEncoder passwordEncoder;
    private final BranchServiceImp branchServiceImp;
    private final DocumentFeedbackServiceImp documentFeedbackServiceImp;
    private final FileService fileService;
    private final FeedbackServiceImp feedbackServiceImp;
    private final FeedbackMapper feedbackMapper;

    @Override
    public List<Realtor> findAll() {
        return realtorRepository.findAll();
    }

    @Override
    public Optional<Realtor> findById(Long id) {
        return realtorRepository.findById(id);
    }

    @Override
    public Optional<Realtor> findByEmail(String email) {
        return realtorRepository.findByEmail(email);
    }

    @Override
    public Realtor save(Realtor realtor) {
        if (realtor.getPassword() != null)
            realtor.setPassword(passwordEncoder.encode(realtor.getPassword()));
        return realtorRepository.save(realtor);
    }

    @Override
    public void deleteById(Long id) {
        realtorRepository.deleteById(id);
    }

    @Override
    public Page<RealtorTableResponse> getPageableRealtor(int page, Integer size, String id, String code, String fullName, String email, String dateOfBirthday) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return realtorMapper.toRealtorTableResponse(
                realtorRepository.findAll(
                        RealtorSpecification.search(
                                id,
                                code,
                                fullName,
                                email,
                                dateOfBirthday),
                        pageRequest));
    }

    @Override
    public void deleteRealtor(Long id) {
        realtorRepository.deleteById(id);
    }

    @Override
    public Realtor getRealtorById(Long id) {
        return realtorRepository.findById(id).orElseThrow();
    }

    @Override
    public void saveFromRequest(RealtorRequest request) {
        Realtor realtor = realtorMapper.toEntityFromRealtorRequest(request);


        if (realtor.getPassword() != null) {
            realtor.setPassword(passwordEncoder.encode(realtor.getPassword()));
        }

        if (request.getAvatar() != null) {
            try {
                realtor.setPathAvatar(fileService.uploadFile(request.getAvatar()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (request.getDocuments() != null) {
            List<DocumentFeedback> documents = new ArrayList<>();

            for (DocumentFeedbackRequest documentRequest : request.getDocuments()) {
                DocumentFeedback document = new DocumentFeedback();
                document.setName(documentRequest.getName());

                if (documentRequest.getFile() != null) {
                    try {
                        document.setPath(fileService.uploadFile(documentRequest.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                document.setRealtor(realtor);
                documents.add(document);
            }

            realtor.setDocumentFeedbacks(documents);
        }

        if (request.getFeedBacks() != null) {
            List<Feedback> feedbacks = new ArrayList<>();

            for (FeedbackRequest feedbackRequest : request.getFeedBacks()) {
                Feedback feedback = new Feedback();
                feedback.setName(feedbackRequest.getName());
                feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
                feedback.setDescription(feedbackRequest.getDescription());
                feedback.setRealtor(realtor);
                feedbacks.add(feedback);
                feedbackServiceImp.save(feedback);
            }

            realtor.setFeedBacks(feedbacks);
        }
        Realtor savedRealtor = save(realtor);
        if (request.getBranchIds() != null) {
            ArrayList<Branch> branches = new ArrayList<>();
            for (Long l : request.getBranchIds()) {
                Branch branchById = branchServiceImp.getBranchById(l);
                branches.add(branchById);
                if (branchById.getRealtors() == null) {
                    branchById.addRealtor(savedRealtor);
                } else {
                    List<Realtor> realtors = new ArrayList<>();
                    realtors.add(savedRealtor);
                    branchById.setRealtors(realtors);
                }

            }
            realtor.setBranches(
                    branches
            );
        }

        if (request.getPhoneNumbers() != null) {
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            for (PhoneNumberResponse phoneResponse : request.getPhoneNumbers()) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setId(phoneResponse.getId());
                phoneNumber.setPhoneNumber(phoneResponse.getPhoneNumber());
                phoneNumber.setContactType(phoneResponse.getContactType());
                phoneNumber.setRealtor(realtor);
                phoneNumbers.add(phoneNumber);
            }
            realtor.setPhoneNumbers(phoneNumbers);
        }

        save(realtor);
    }

    @Override
    public void updateRealtor(RealtorRequest realtorRequest) {
        Realtor oldRealtor = realtorRepository.findById(realtorRequest.getId()).orElseThrow();

        Realtor realtor = realtorMapper.toUpdateEntityFromRealtorRequest(oldRealtor,
                realtorRequest);

        if (realtorRequest.getAvatar() != null) {
            try {
                realtor.setPathAvatar(fileService.uploadFile(realtorRequest.getAvatar()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            realtor.setPathAvatar(oldRealtor.getPathAvatar());
        }

        List<DocumentFeedback> updatedDocuments = new ArrayList<>();
        if (realtorRequest.getDocuments() != null) {
            Map<Long, DocumentFeedbackRequest> requestDocumentsMap = realtorRequest.getDocuments().stream()
                    .filter(doc -> doc.getId() != null)
                    .collect(Collectors.toMap(DocumentFeedbackRequest::getId, doc -> doc));

            if (oldRealtor.getDocumentFeedbacks() != null) {
                for (DocumentFeedback oldDoc : oldRealtor.getDocumentFeedbacks()) {
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

                        oldDoc.setRealtor(realtor);
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
            for (DocumentFeedbackRequest documentRequest : realtorRequest.getDocuments()) {
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

                    newDocument.setRealtor(realtor);
                    updatedDocuments.add(newDocument);
                }
            }

            realtor.setDocumentFeedbacks(updatedDocuments);
        }

        if (realtorRequest.getFeedBacks() != null) {
            List<Feedback> updatedFeedbacks = new ArrayList<>();

            Map<Long, FeedbackRequest> requestFeedbacksMap = realtorRequest.getFeedBacks().stream()
                    .filter(feedback -> feedback.getId() != null)
                    .collect(Collectors.toMap(FeedbackRequest::getId, feedback -> feedback));

            if (oldRealtor.getFeedBacks() != null) {
                for (Feedback oldFeedback : oldRealtor.getFeedBacks()) {
                    if (!requestFeedbacksMap.containsKey(oldFeedback.getId())) {
                        feedbackServiceImp.deleteById(oldFeedback.getId());
                    }
                }
            }

            for (FeedbackRequest feedbackRequest : realtorRequest.getFeedBacks()) {
                Feedback feedback = new Feedback();
                if (feedbackRequest.getId() != null) {
                    feedback.setId(feedbackRequest.getId());
                }
                feedback.setName(feedbackRequest.getName());
                feedback.setPhoneNumber(feedbackRequest.getPhoneNumber());
                feedback.setDescription(feedbackRequest.getDescription());
                feedback.setRealtor(realtor);
                updatedFeedbacks.add(feedback);
                feedbackServiceImp.save(feedback);
            }

            realtor.setFeedBacks(updatedFeedbacks);
        }

        List<Branch> newBranches = realtorRequest.getBranchIds().stream()
                .map(branchServiceImp::getBranchById)
                .collect(Collectors.toCollection(ArrayList::new));

        if (oldRealtor.getBranches() != null) {
            for (Branch oldBranch : oldRealtor.getBranches()) {
                if (!newBranches.contains(oldBranch)) {
                    oldBranch.getRealtors().remove(oldRealtor);
                }
            }
        }

        realtor.setBranches(newBranches);

        for (Branch branch : newBranches) {
            if (branch.getRealtors() == null) {
                branch.setRealtors(new ArrayList<>());
            }
            if (!branch.getRealtors().contains(realtor)) {
                branch.getRealtors().add(realtor);
            }
        }

        if (realtorRequest.getPhoneNumbers() != null) {
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            for (PhoneNumberResponse phoneResponse : realtorRequest.getPhoneNumbers()) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setId(phoneResponse.getId());
                phoneNumber.setPhoneNumber(phoneResponse.getPhoneNumber());
                phoneNumber.setContactType(phoneResponse.getContactType());
                phoneNumber.setRealtor(realtor);
                phoneNumbers.add(phoneNumber);
            }
            realtor.setPhoneNumbers(phoneNumbers);
        }

        realtor.setDateOfBirthday(DateUtil.toFormatDateToDB(realtorRequest.getDateOfBirthday(),"dd.MMM.yy"));
        realtor.setCode(realtorRequest.getCode());
        realtorRepository.save(realtor);
    }
}
