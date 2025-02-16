package com.capstone.ar_guideline.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

  private static Path fileStorageLocation;

  // Constructor initializes the storage location
  public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
    fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    try {
      Files.createDirectories(fileStorageLocation);
    } catch (IOException e) {
      throw new RuntimeException("Could not create the upload directory!", e);
    }
  }

  // Static method to store a file
  public static String storeFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("Cannot store an empty file.");
    }

    try {
      String originalFilename = file.getOriginalFilename();
      String fileExtension = "";

      if (originalFilename != null) {
        originalFilename =
            Paths.get(originalFilename).getFileName().toString(); // Sanitize filename
        if (originalFilename.contains(".")) {
          fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
      }

      String fileId = UUID.randomUUID().toString() + fileExtension;
      Path targetLocation = fileStorageLocation.resolve(fileId);

      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return fileId;
    } catch (IOException e) {
      throw new RuntimeException("Could not store file: " + file.getOriginalFilename(), e);
    }
  }

  // Static method to retrieve a file
  public static Resource getFile(String filename) {
    try {
      Path filePath = fileStorageLocation.resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (!resource.exists() || !resource.isReadable()) {
        throw new RuntimeException("File not found: " + filename);
      }

      return resource;
    } catch (Exception e) {
      throw new RuntimeException("Error retrieving file: " + filename, e);
    }
  }

  // Static method to delete a file
  public static void deleteFile(String filename) {
    try {
      Path filePath = fileStorageLocation.resolve(filename).normalize();
      Files.deleteIfExists(filePath);
    } catch (IOException e) {
      throw new RuntimeException("Could not delete file: " + filename, e);
    }
  }
  public static String storeZipFile(InputStream inputStream) {
    try {
      String fileId = UUID.randomUUID().toString() + ".zip";
      Path targetLocation = fileStorageLocation.resolve(fileId);

      Files.createDirectories(targetLocation.getParent());
      Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return fileId;
    } catch (IOException e) {
      throw new RuntimeException("Could not store ZIP file", e);
    }
  }
}
