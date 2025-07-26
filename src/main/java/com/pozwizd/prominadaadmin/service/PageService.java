package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.models.page.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PageService {

    PageResponse createPage(@Valid PageResponse pageResponse);

    PageResponse getPageById(Long id);

    PageResponse updatePage(Long id, @Valid PageResponse pageResponse);

    boolean deletePage(Long id);

    Page<PageResponse> getPages(int page, Integer size);
}
