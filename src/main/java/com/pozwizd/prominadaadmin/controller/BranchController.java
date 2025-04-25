package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.branch.BranchResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.BranchServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/branch")
@AllArgsConstructor
public class BranchController {

    private final BranchServiceImp branchServiceImp;


    @GetMapping("/list")
    public @ResponseBody List<BranchResponse> getBranches() {
        return branchServiceImp.findAllResponse();
    }

}
