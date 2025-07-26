package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandFile;
import com.pozwizd.prominadaadmin.repository.primary.ResidentialLandFileRepository;
import com.pozwizd.prominadaadmin.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Added
import org.mapstruct.Named;
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
@RequiredArgsConstructor
@Slf4j
public class FileServiceImp implements FileService {


    @Value("${file.upload.dir}")
    private String uploadDir;

    private final ResidentialLandFileRepository residentialLandFileRepository;


    // Условие для проверки валидности файла
    @Named("isValidFile")
    public boolean isValidFile(MultipartFile file) {
        return file != null &&
                !file.isEmpty() &&
                file.getSize() > 0 &&
                file.getOriginalFilename() != null &&
                !file.getOriginalFilename().trim().isEmpty();
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

    @Override
    @Named("uploadFile")
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir +"/uploads/", fileName);
        Files.copy(file.getInputStream(), filePath);
        return "uploads/" + fileName;
    }

    // Новый метод для условной загрузки файла
    @Named("uploadFileIfPresent")
    public String uploadFileIfPresent(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null; // Возвращаем null, чтобы MapStruct игнорировал это поле
        }
        return uploadFile(file);
    }


    /**
     * Удаляет файл из системы по его имени.
     *
     * @param fileName Имя файла для удаления
     * @return true, если файл был успешно удален, false если файл не существует
     * @throws IOException если произошла ошибка при удалении файла
     */
    @Override
    @Named("deleteFile")
    public boolean deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            return true;
        }
        return false;
    }

    @Override
    public void saveResidentialLandFile(MultipartFile file, ResidentialLand residentialLand) {
        try {
            String filePath = uploadFile(file);
            ResidentialLandFile residentialLandFile = new ResidentialLandFile();
            residentialLandFile.setFilePath(filePath);
            residentialLandFile.setResidentialLand(residentialLand);
            residentialLandFileRepository.save(residentialLandFile);
        } catch (IOException e) {
            log.error("Failed to store file {}: {}", file.getOriginalFilename(), e.getMessage()); // Changed logging
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
