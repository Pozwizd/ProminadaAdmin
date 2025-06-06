package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentFeedbackRepository extends JpaRepository<DocumentFeedback, Long> {
    List<DocumentFeedback> findByPersonalId(Long personalId);
    List<DocumentFeedback> findByRealtorId(Long realtorId);
}