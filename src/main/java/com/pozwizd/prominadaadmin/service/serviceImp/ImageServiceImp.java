package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageServiceImp implements ImageService {
    @Override
    public void init(Path root) {
//        LogUtil.logInitNotification(root.toString());
        try {
            Files.createDirectories(root);
//            LogUtil.logInfo("Built package by path: " + root);
        } catch (IOException e) {
//            LogUtil.logError("Failed to initialize folder for upload at " + root, e);
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
//                    LogUtil.logInfo("Attempting to save file: " + file.getOriginalFilename() + " to path: " + path);
                    Files.copy(file.getInputStream(), path_);
//                    LogUtil.logInfo("File saved successfully to path: " + path_);
                }
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
//                LogUtil.logError("A file of that name already exists at " + path, e);
            }
//            LogUtil.logError("Error occurred while saving file to " + path, e);
        }
    }

    @Override
    public void deleteByPath(String path) throws IOException {
//        LogUtil.logInfo("Attempting to delete file/folder at path: " + path);
        Path path_ = Path.of("." + path);
        Path lockFile = Path.of(path_ + ".lock");
        if (Files.exists(lockFile)) {
//            LogUtil.logWarning("Deletion blocked: lock file exists for " + path);
            return;
        }

        if (path != null && !path.isEmpty() && Files.exists(path_)) {
            Files.walkFileTree(path_, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
//                    LogUtil.logInfo("Deleted file: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
//                    LogUtil.logInfo("Deleted directory: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
//            LogUtil.logWarning("Path not found: " + path);
        }
    }


    @Override
    public String generateFileName(MultipartFile file) {
        String generatedFileName = UUID.randomUUID() + "." + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//        LogUtil.logInfo("Generated file name: " + generatedFileName + " for original file: " + file.getOriginalFilename());
        return generatedFileName;
    }
}
