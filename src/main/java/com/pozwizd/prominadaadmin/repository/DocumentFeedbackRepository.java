package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.DocumentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFeedbackRepository extends JpaRepository<DocumentFeedback, Long> {
}