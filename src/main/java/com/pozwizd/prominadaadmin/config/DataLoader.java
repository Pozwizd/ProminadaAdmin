package com.pozwizd.prominadaadmin.config;

import com.pozwizd.prominadaadmin.entity.*;
import com.pozwizd.prominadaadmin.service.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final PersonalService personalService;
    private final FeedbackService feedbackService;
    private final BranchService branchService;
    private final FileService fileService;
    private final DocumentFeedbackService documentFeedbackService;
    private final Faker faker;

    @EventListener(ApplicationReadyEvent.class)
    public void loadEntity() {
        loadBranch();
        loadAdmin();
        loadPersonal();
        loadDocumentFeedback();
    }

    private void loadBranch() {
        for (int i = 0; i < 10; i++) {
            Branch branch = new Branch();
            branch.setName(faker.company().name());
            branch.setPhoneNumber(faker.phoneNumber().phoneNumber());
            branch.setEmail(faker.internet().emailAddress());
            branch.setAddress(faker.address().fullAddress());
            branchService.save(branch);
        }
    }

    public void loadAdmin() {
        Personal personal = new Personal();
        personal.setName("Роман");
        personal.setSurname("SpaceLab");
        personal.setLastName("Батькович");
        personal.setEmail("admin@gmail.com");
        personal.setPhoneNumber("+380123456789");
        personal.setPassword("admin");
        personal.setRole(Role.ADMIN);

        File avatarFile = new File("uploads/avatar.jpg");
        if (avatarFile.exists() && avatarFile.isFile()) {
            personal.setPathAvatar("uploads/avatar.jpg");
        }

        Branch branch = branchService.getBranchById(1L);
        personal.setBranches(List.of(branch));

        Personal savedPersonal = personalService.save(personal);
        branch.setPersonals(List.of(savedPersonal));
        branchService.save(branch);
        for (int j = 0; j < 5; j++) {
            Feedback feedback1 = new Feedback();
            feedback1.setName(faker.name().firstName());
            feedback1.setPhoneNumber(faker.phoneNumber().phoneNumber());
            feedback1.setDescription(faker.lorem().sentence(10));
            feedback1.setPersonal(savedPersonal);
            feedbackService.save(feedback1);
        }
    }

    public void loadDocumentFeedback() {
        List<DocumentFeedback> documentFeedbacks = new java.util.ArrayList<>();
        File dir = new File("uploads/");
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                Personal personal = personalService.getPersonalById(1L);
                for (File file : files) {
                    if (file.getName().endsWith(".pdf")) {
                        DocumentFeedback feedback = new DocumentFeedback();
                        feedback.setName(file.getName());
                        feedback.setPath("uploads/"+file.getName());
                        feedback.setPersonal(personal);

                        documentFeedbacks.add(feedback);
                    }
                }

                documentFeedbacks.forEach(feedback -> feedback.setPersonal(personal));
                documentFeedbackService.saveAllDocumentFeedback(documentFeedbacks);
                personal.setDocumentFeedbacks(documentFeedbacks);
                personalService.save(personal);

            }
        }
    }

    public void loadPersonal() {
        Faker faker = new Faker(new Locale("uk"));
        for (int i = 0; i < 100; i++) {
            Personal personal = new Personal();
            personal.setName(faker.name().firstName());
            personal.setSurname(faker.name().lastName());
            personal.setEmail(faker.internet().emailAddress());
            personal.setPhoneNumber(faker.phoneNumber().phoneNumber());
            personal.setPassword(faker.internet().password());
            personal.setRole(Role.USER);

            Personal savedPersonal = personalService.save(personal);
            for (int j = 0; j < 5; j++) {
                Feedback feedback = new Feedback();
                feedback.setName(savedPersonal.getName());
                feedback.setPhoneNumber(savedPersonal.getPhoneNumber());
                feedback.setDescription(faker.lorem().sentence(10));
                feedback.setPersonal(savedPersonal);
                feedbackService.save(feedback);
            }
        }
    }


}