package com.shopme.admin.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            LOGGER.error("Could not save file: " + fileName, ex);
            throw new IOException("Could Not Save file: " + fileName, ex);
        }
    }

    public static void cleanDir(String dir) throws IOException {
        try {
            Path dirPath = Paths.get(dir);
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        LOGGER.error("Could not delete file" + e.getMessage());
                        throw new IllegalArgumentException("Could not delete file" + e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            LOGGER.error("Could not delete files", e);
            throw new IOException("Could not delete files", e);
        }
    }

    public static String renameFile(String fileName) {
        try {
            // Generate a random UUID to append to the file name
            return  java.util.UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        } catch (Exception e) {
            LOGGER.error("Could not rename file: " + fileName, e);
            throw new IllegalArgumentException("Could not rename file: " + fileName, e);
        }
    }

    public static void removeDir(String dir) throws IOException {
        try{
            Path dirPath = Paths.get(dir);
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Files.delete(dirPath);
        } catch (IOException e) {
            LOGGER.error("Could not delete directory: " + dir, e);
            throw new IOException("Could not delete directory: " + dir, e);
        }
    }

    public static boolean isDirExists(String uploadDir) {
       try {
            Path uploadPath = Paths.get(uploadDir);
            return Files.exists(uploadPath);
       } catch (Exception e) {
           LOGGER.error("Could not check directory: " + uploadDir, e);
           throw new IllegalArgumentException("Could not check directory: " + uploadDir, e);
       }
    }

    public static void removeFile(String dir, String fileName) throws IOException {
        try {
            Path filePath = Paths.get(dir + "/" + fileName);
            Files.delete(filePath);
        } catch (IOException e) {
            LOGGER.error("Could not delete file: " + fileName, e);
            throw new IOException("Could not delete file: " + fileName, e);
        }
    }

}
