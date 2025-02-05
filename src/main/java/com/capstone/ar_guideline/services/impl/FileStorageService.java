package com.capstone.ar_guideline.services.impl;

import java.io.IOException;
import java.nio.file.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

  private final Path fileStorageLocation;

  public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
    this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (Exception e) {
      throw new RuntimeException("Could not create the upload directory!", e);
    }
  }

  public String storeFile(MultipartFile file) {
    try {
      Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return targetLocation.toString();
    } catch (IOException e) {
      throw new RuntimeException("Could not store file " + file.getOriginalFilename(), e);
    }
  }

  public Resource getFile(String filename) {
    try {
      Path filePath = fileStorageLocation.resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (!resource.exists() || !resource.isReadable()) {
        throw new NoSuchFileException("File not found: " + filename);
      }

      return resource;
    } catch (Exception e) {
      throw new RuntimeException("Error retrieving file: " + filename, e);
    }
  }
}
