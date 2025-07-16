package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandGalleryImage;
import com.pozwizd.prominadaadmin.repository.primary.ResidentialLandGalleryImageRepository;
import com.pozwizd.prominadaadmin.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Added

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j // Added
public class ImageServiceImp implements ImageService {

    private final ResidentialLandGalleryImageRepository residentialLandGalleryImageRepository;

    @Override
    public void init(Path root) {
        try {
            Files.createDirectories(root);
            log.info("Built package by path: {}", root);
        } catch (IOException e) {
            log.error("Failed to initialize folder for upload at {}: {}", root, e.getMessage());
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file, String path) {
        try {
            if (path != null && !path.isEmpty()) {
                Path path_ = Path.of(path);
                if (!Files.exists(path_.getParent())) {
                    init(path_.getParent());
                }
                if (file != null) {
                    log.info("Attempting to save file: {} to path: {}", file.getOriginalFilename(), path);
                    Files.copy(file.getInputStream(), path_);
                    log.info("File saved successfully to path: {}", path_);
                }
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                log.error("A file of that name already exists at {}: {}", path, e.getMessage());
            }
            log.error("Error occurred while saving file to {}: {}", path, e.getMessage());
        }
    }

    @Override
    public void deleteByPath(String path) throws IOException {
        log.info("Attempting to delete file/folder at path: {}", path);
        Path path_ = Path.of("." + path);
        Path lockFile = Path.of(path_ + ".lock");
        if (Files.exists(lockFile)) {
            log.warn("Deletion blocked: lock file exists for {}", path);
            return;
        }

        if (path != null && !path.isEmpty() && Files.exists(path_)) {
            Files.walkFileTree(path_, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    log.info("Deleted file: {}", file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    log.info("Deleted directory: {}", dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            log.warn("Path not found: {}", path);
        }
    }


    @Override
    public String generateFileName(MultipartFile file) {
        String generatedFileName = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.info("Generated file name: {} for original file: {}", generatedFileName, file.getOriginalFilename());
        return generatedFileName;
    }

    @Override
    public void saveResidentialLandGalleryImage(MultipartFile file, ResidentialLand residentialLand) {
        try {
            String fileName = generateFileName(file);
            String filePath = "uploads/" + fileName;
            save(file, filePath);

            ResidentialLandGalleryImage galleryImage = new ResidentialLandGalleryImage();
            galleryImage.setPathImage(filePath); // Changed to setPathImage
            galleryImage.setResidentialLand(residentialLand);
            residentialLandGalleryImageRepository.save(galleryImage);
        } catch (Exception e) {
            log.error("Failed to store image {}: {}", file.getOriginalFilename(), e.getMessage());
            throw new RuntimeException("Failed to store image " + file.getOriginalFilename(), e);
        }
    }
}
