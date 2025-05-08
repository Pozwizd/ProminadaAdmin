package com.pozwizd.prominadaadmin.mapper;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.models.branch.BranchResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchMapper {
    Branch toEntity(BranchResponse branchResponse);

    BranchResponse toBranchResponse(Branch branch);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Branch partialUpdate(BranchResponse branchResponse, @MappingTarget Branch branch);

    default Page<BranchResponse> toBranchResponse(Page<Branch> branchPage) {
        return branchPage.map(this::toBranchResponse);
    }

    default List<BranchResponse> toBranchResponse(List<Branch> branches) {
        return branches.stream().map(this::toBranchResponse).toList();
    }
}