package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.banner.BannerRequest;
import com.pozwizd.prominadaadmin.models.branch.BranchRequest;
import com.pozwizd.prominadaadmin.service.BannerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannersController {


    private final BannerService bannerService;

    @GetMapping
    public ModelAndView showBannersPage(Model model) {
        

        model.addAttribute("pageTitle", "banners.name");
        model.addAttribute("pageActive", "banners");
        model.addAttribute("opened", true);

        return new ModelAndView("banner/banners");
    }

    @GetMapping("/getAll")
    public @ResponseBody ResponseEntity<?> getAllBanners(){
        bannerService.getAllBannerResponse();
        return ResponseEntity.ok().body(bannerService.getAllBannerResponse());
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "banners.name");
        model.addAttribute("pageActive", "banners.edit");
        model.addAttribute("isEdit", true);
        model.addAttribute("branchId", id);
        return new ModelAndView("banner/bannerForm");
    }


    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getBannerById(@PathVariable Long id){
        return ResponseEntity.ok().body(bannerService.getById(id));
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public @ResponseBody ResponseEntity<?> updateBanner(@Valid @ModelAttribute
                                                        BannerRequest bannerRequest) {

        try {
            bannerService.update(bannerRequest);
            return ResponseEntity.ok("Banner successfully created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while updating banner: " + e.getMessage());
        }
    }



}
