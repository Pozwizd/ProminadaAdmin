package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.*;
import com.pozwizd.prominadaadmin.mapper.FeedbackMapper;
import com.pozwizd.prominadaadmin.mapper.RealtorMapper;
import com.pozwizd.prominadaadmin.models.documentFeedback.DocumentFeedbackRequest;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorRequest;
import com.pozwizd.prominadaadmin.models.realtor.RealtorTableResponse;
import com.pozwizd.prominadaadmin.repository.RealtorRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import com.pozwizd.prominadaadmin.service.RealtorService;
import com.pozwizd.prominadaadmin.specification.RealtorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Page<RealtorTableResponse> getPageableRealtor(int page, Integer size, String code, String fullName, String email, String dateOfBirthday) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return realtorMapper.toRealtorTableResponse(
                realtorRepository.findAll(
                        RealtorSpecification.search(
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
                if (branchById.getPersonals() == null) {
                    branchById.addRealtor(savedRealtor);
                } else {
                    List<Realtor> personals = new ArrayList<>();
                    personals.add(savedRealtor);
                    branchById.setRealtors(personals);
                }

            }
            realtor.setBranches(
                    branches
            );
        }

        save(realtor);
    }

    @Override
    public void updateRealtor(RealtorRequest personalRequest) {

    }
}
