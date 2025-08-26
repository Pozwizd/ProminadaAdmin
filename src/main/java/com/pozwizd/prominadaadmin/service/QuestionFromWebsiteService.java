package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.QuestionFromWebsite;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface QuestionFromWebsiteService {
    QuestionFromWebsite create(@Valid QuestionFromWebsite questionFromWebsite);

    QuestionFromWebsite getById(Long id);

    Boolean deleteById(Long id);

    Page<QuestionFromWebsite> getByPagination(int page, Integer size);
}
