package com.pozwizd.prominadaadmin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Сервис для управления файлами в системе.
 * Обеспечивает функционал загрузки, хранения, получения и удаления файлов.
 * Поддерживает работу как с временными, так и с постоянными файлами.
 */
@Service
public class FileService {
    @Value("${file.upload.dir}")
    private String uploadDir;


    public FileService() {
        createUploadDirectories();
    }

    private void createUploadDirectories() {
        try {
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create directories!", e);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    /**
     * Удаляет файл из системы по его имени.
     *
     * @param fileName Имя файла для удаления
     * @return true, если файл был успешно удален, false если файл не существует
     * @throws IOException если произошла ошибка при удалении файла
     */
    public boolean deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            return true;
        }
        return false;
    }
}
