package com.pozwizd.prominadaadmin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file) throws IOException;

    boolean deleteFile(String fileName) throws IOException;
}