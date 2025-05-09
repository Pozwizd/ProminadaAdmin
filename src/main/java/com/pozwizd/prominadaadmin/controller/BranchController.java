package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.entity.Branch;
import com.pozwizd.prominadaadmin.models.branch.BranchRequest;
import com.pozwizd.prominadaadmin.models.branch.BranchResponse;
import com.pozwizd.prominadaadmin.service.serviceImp.BranchServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/branch")
@AllArgsConstructor
public class BranchController {

    private final BranchServiceImp branchServiceImp;

    @GetMapping
    public ModelAndView getBranchPage(Model model) {

        model.addAttribute("pageTitle", "branch");
        model.addAttribute("pageActive", "branch");

        return new ModelAndView("branch/branches");
    }

    // Не менять и не удалять, используется в js скриптах на фронт
    @GetMapping("/list")
    public @ResponseBody List<BranchResponse> getBranches() {
        return branchServiceImp.findAllResponse();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return new ResponseEntity<>(branchServiceImp.getBranchById(id), HttpStatus.OK);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBranch(@PathVariable Long id, @Valid @ModelAttribute BranchRequest branchRequest) {
        if (branchRequest.getId() != null && !branchRequest.getId().equals(id)) {
            return ResponseEntity.badRequest().body("ID в пути и в теле запроса не совпадают");
        }

        try {
            branchServiceImp.updateBranch(id, branchRequest);
            return ResponseEntity.ok("Филиал успешно обновлен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при обновлении филиала: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchServiceImp.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "branch.editBranch");
        model.addAttribute("pageActive", "branch");
        model.addAttribute("isEdit", true);
        model.addAttribute("branchId", id);
        return new ModelAndView("branch/branchForm");
    }
    
    @GetMapping("/create")
    public ModelAndView showCreateForm(Model model) {
        model.addAttribute("pageTitle", "branch.createBranch");
        model.addAttribute("pageActive", "branch");
        model.addAttribute("isEdit", false);
        return new ModelAndView("branch/branchForm");
    }
    
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveBranch(@Valid @ModelAttribute BranchRequest branchRequest) {
        try {
            if (branchRequest.getId() != null) {
                branchServiceImp.updateBranch(branchRequest);
            } else {
                branchServiceImp.createBranch(branchRequest);
            }
            return ResponseEntity.ok().body(java.util.Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(java.util.Map.of(
                "success", false, 
                "message", "Ошибка при сохранении филиала: " + e.getMessage()));
        }
    }


    @GetMapping("/getAllBranches")
    public @ResponseBody ResponseEntity<Page<BranchResponse>> getPageableBranch(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false, name = "filter_code") String code,
            @RequestParam(required = false, name = "filter_name") String name,
            @RequestParam(required = false, name = "filter_address") String address) {
        return new ResponseEntity<>(
                branchServiceImp.getPageableBranch(
                        page, size, code, name, address), HttpStatus.OK);
    }
}
