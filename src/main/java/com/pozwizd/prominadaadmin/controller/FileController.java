package com.pozwizd.prominadaadmin.controller;

import com.pozwizd.prominadaadmin.models.FileInfo;
import com.pozwizd.prominadaadmin.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload-temp")
    public ResponseEntity<FileInfo> uploadTempFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam(value = "userId", required = false) Long userId) {
        FileInfo fileInfo = fileService.storeTempFile(file, userId);
        return ResponseEntity.ok(fileInfo);
    }

    @GetMapping("/download-temp/{id}")
    public ResponseEntity<Resource> downloadTempFile(@PathVariable String id) {
        Resource file = fileService.loadTempFileAsResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @DeleteMapping("/delete-temp/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTempFile(@PathVariable String id) {
        boolean deleted = fileService.deleteTempFile(id);
        return ResponseEntity.ok(Map.of("deleted", deleted));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Resource file = fileService.loadFileAsResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}