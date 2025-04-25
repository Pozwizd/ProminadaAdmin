package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.models.branch.BranchResponse;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

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

    Page<BranchResponse> getPageableBranch(int page, Integer size,
                                           String code,
                                           String name,
                                           String address,
                                           String phoneNumber,
                                           String email);

    Branch getBranchById(Long id);
}
