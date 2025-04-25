package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Feedback;
import com.pozwizd.prominadaadmin.models.feedback.FeedbackResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FeedbackService  {
    List<Feedback> findAll();

    Optional<Feedback> findById(Long id);

    Feedback save(Feedback feedback);

    void deleteById(Long id);

    Page<FeedbackResponse> getPageableFeedback(int page, Integer size,
                                               String name,
                                               String phoneNumber,
                                               String description);

    Feedback getFeedbackById(Long id);
}