package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.Page;
import com.pozwizd.prominadaadmin.exception.OperationException;
import com.pozwizd.prominadaadmin.mapper.PageMapper;
import com.pozwizd.prominadaadmin.models.page.PageResponse;
import com.pozwizd.prominadaadmin.repository.primary.PageRepository;
import com.pozwizd.prominadaadmin.service.PageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final PageMapper pageMapper;

    @Override
    public Page createPage(Page page) {
        return pageRepository.save(page);
    }

    @Override
    public PageResponse createPage(PageResponse pageResponse) {
        try {
            Page page = pageRepository.save(pageMapper.toEntity(pageResponse));
            log.info("Страница с ID {} успешно создана", page.getId());
            return pageMapper.toDto(page);
        } catch (Exception e) {
            log.error("Ошибка при создании страницы", e);
            throw new OperationException("создании страницы", e.getMessage());
        }
    }

    @Override
    public PageResponse getPageById(Long id) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new OperationException("получении страницы",
                        "Страница с ID " + id + " не найдена", HttpStatus.NOT_FOUND));
        log.info("Страница с ID {} успешно получена", id);
        return pageMapper.toDto(page);
    }

    @Override
    public PageResponse updatePage(Long id, PageResponse pageResponse) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new OperationException("обновлении страницы",
                        "Страница с ID " + id + " не найдена"));

        pageMapper.partialUpdate(pageResponse, page);
        Page updatedPage = pageRepository.save(page);
        log.info("Страница с ID {} успешно обновлена", updatedPage.getId());
        return pageMapper.toDto(updatedPage);
    }


    @Override
    public boolean deletePage(Long id) {
        if (!pageRepository.existsById(id)) {
            throw new OperationException("удалении страницы",
                    "Страница с ID " + id + " не найдена");
        }
        pageRepository.deleteById(id);
        log.info("Страница с ID {} успешно удалена", id);
        return true;
    }

    @Override
    public org.springframework.data.domain.Page<PageResponse> getPages(int page, Integer size) {
        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<Page> pages = pageRepository.findAll(pageRequest);
        return pages.map(pageMapper::toDto);
    }
}

