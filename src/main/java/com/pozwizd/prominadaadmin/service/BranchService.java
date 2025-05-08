package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.models.branch.BranchRequest;
import com.pozwizd.prominadaadmin.models.branch.BranchResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BranchService {
    List<Branch> findAll();

    List<BranchResponse> findAllResponse();

    Optional<Branch> findById(Long id);

    Optional<Branch> findByCode(String code);

    Optional<Branch> findByName(String name);

    Optional<Branch> findByEmail(String email);

    @Transactional
    Branch save(Branch branch);

    @Transactional
    void deleteById(Long id);


    Page<BranchResponse> getPageableBranch(int page,
                                           Integer size,
                                           String code,
                                           String name,
                                           String address);

    Branch getBranchById(Long id);
    
    /**
     * Создает новый филиал.
     *
     * @param branchRequest Данные нового филиала
     * @throws IOException если возникла ошибка при обработке файла изображения
     */
    @Transactional
    void createBranch(@Valid BranchRequest branchRequest) throws IOException;

    /**
     * Обновляет информацию о филиале.
     *
     * @param branchRequest Данные филиала для обновления
     * @throws IOException если возникла ошибка при обработке файла изображения
     */
    @Transactional
    void updateBranch(@Valid BranchRequest branchRequest) throws IOException;

    /**
     * Обновляет информацию о филиале по ID.
     *
     * @param id ID филиала для обновления
     * @param branchRequest Данные филиала для обновления
     * @throws IOException если возникла ошибка при обработке файла изображения
     */
    @Transactional
    void updateBranch(Long id, @Valid BranchRequest branchRequest) throws IOException;
}
