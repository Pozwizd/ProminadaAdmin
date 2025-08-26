package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {
    @Named("uploadFile")
    String uploadFile(MultipartFile file) throws IOException;

    @Named("uploadFileIfPresent")
    String uploadFileIfPresent(MultipartFile file) throws IOException;

    @Named("deleteFile")
    boolean deleteFile(String fileName) throws IOException;

    void saveResidentialLandFile(MultipartFile file, ResidentialLand residentialLand);
}