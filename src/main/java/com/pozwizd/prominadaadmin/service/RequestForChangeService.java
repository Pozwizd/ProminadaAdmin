package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.RequestForChange;
import com.pozwizd.prominadaadmin.models.page.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RequestForChangeService {

    RequestForChange create(@Valid RequestForChange requestForChange);

    RequestForChange getById(Long id);

    Boolean deleteById(Long id);

    Page<RequestForChange> getByPagination(int page, Integer size);
}
