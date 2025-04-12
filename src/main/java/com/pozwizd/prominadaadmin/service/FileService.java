package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.models.FileInfo;
import com.pozwizd.prominadaadmin.repository.PersonalRepository;
import com.pozwizd.prominadaadmin.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    @Value("${file.temp.dir}")
    private String tempDir;

    private final FileInfoRepository fileInfoRepository;

    private final PersonalRepository personalRepository;

    public FileService(FileInfoRepository fileInfoRepository, PersonalRepository personalRepository) {
        createUploadDirectories();
        this.fileInfoRepository = fileInfoRepository;
        this.personalRepository = personalRepository;
    }

    private void createUploadDirectories() {
        try {
            Path uploadPath = Paths.get("uploads");
            Path tempPath = Paths.get("temp-uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            if (!Files.exists(tempPath)) {
                Files.createDirectories(tempPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create directories!", e);
        }
    }

    /**
     * Сохраняет файл во временное хранилище.
     * 
     * @param file Загружаемый файл
     * @param userId ID пользователя, который загружает файл (может быть null)
     * @return {@link FileInfo} Информация о сохраненном файле
     * @throws RuntimeException если произошла ошибка при сохранении файла
     */
    public FileInfo storeTempFile(MultipartFile file, Long userId) {
        try {
            String tempId = UUID.randomUUID().toString();
            String filename = tempId + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(tempDir).resolve(filename);
            
            Files.copy(file.getInputStream(), filePath);
            
            FileInfo fileInfo = FileInfo.builder()
                    .name(filename)
                    .originalFilename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .path(filePath.toString())
                    .tempId(tempId)
                    .temporary(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            if (userId != null) {
                Optional<Personal> userOpt = personalRepository.findById(userId);
                userOpt.ifPresent(fileInfo::setPersonal);
            }
            
            return fileInfoRepository.save(fileInfo);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл", e);
        }
    }

    /**
     * Загружает временный файл как ресурс.
     * 
     * @param id Временный идентификатор файла
     * @return Ресурс файла
     * @throws RuntimeException если файл не найден или произошла ошибка при загрузке
     */
    public Resource loadTempFileAsResource(String id) {
        try {
            FileInfo fileInfo = fileInfoRepository.findByTempId(id)
                    .orElseThrow(() -> new RuntimeException("Файл не найден"));
            
            Path filePath = Paths.get(fileInfo.getPath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Файл не найден");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Файл не найден", e);
        }
    }

    /**
     * Удаляет временный файл.
     * 
     * @param id Временный идентификатор файла
     * @return true если файл успешно удален, false в противном случае
     * @throws RuntimeException если произошла ошибка при удалении файла
     */
    public boolean deleteTempFile(String id) {
        try {
            FileInfo fileInfo = fileInfoRepository.findByTempId(id)
                    .orElseThrow(() -> new RuntimeException("Файл не найден"));
            
            Path filePath = Paths.get(fileInfo.getPath());
            boolean deleted = Files.deleteIfExists(filePath);
            
            if (deleted) {
                fileInfoRepository.delete(fileInfo);
            }
            
            return deleted;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось удалить файл", e);
        }
    }

    /**
     * Загружает постоянный файл как ресурс.
     * 
     * @param id ID файла
     * @return Ресурс файла
     * @throws RuntimeException если файл не найден или произошла ошибка при загрузке
     */
    public Resource loadFileAsResource(Long id) {
        try {
            FileInfo fileInfo = fileInfoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Файл не найден"));
            
            Path filePath = Paths.get(fileInfo.getPath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Файл не найден");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Файл не найден", e);
        }
    }


    /**
     * Перемещает временные файлы в постоянное хранилище.
     * 
     * @param tempFileIds Список временных идентификаторов файлов
     * @param personal Пользователь, которому будут принадлежать файлы
     * @return Список обновленной информации о файлах
     * @throws RuntimeException если произошла ошибка при перемещении файлов
     */
    public List<FileInfo> moveFilesToPermanentStorage(List<String> tempFileIds, Personal personal) {
        try {
            List<FileInfo> tempFiles = fileInfoRepository.findByTempIdIn(tempFileIds);
            
            for (FileInfo fileInfo : tempFiles) {
                String newFilename = UUID.randomUUID().toString() + "_" + fileInfo.getOriginalFilename();
                Path newFilePath = Paths.get(uploadDir).resolve(newFilename);
                
                Path oldPath = Paths.get(fileInfo.getPath());
                Files.move(oldPath, newFilePath);
                
                fileInfo.setPath(newFilePath.toString());
                fileInfo.setName(newFilename);
                fileInfo.setTemporary(false);
                fileInfo.setTempId(null);
                fileInfo.setPersonal(personal);
            }
            
            return fileInfoRepository.saveAll(tempFiles);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось переместить файлы", e);
        }
    }

    /**
     * Удаляет несколько файлов по их идентификаторам.
     * 
     * @param fileIds Список ID файлов для удаления
     */
    public void deleteFiles(List<Long> fileIds) {
        List<FileInfo> files = fileInfoRepository.findAllById(fileIds);
        
        for (FileInfo file : files) {
            try {
                Path filePath = Paths.get(file.getPath());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Не удалось удалить файл: " + file.getPath());
            }
        }
        
        fileInfoRepository.deleteAllById(fileIds);
    }
}