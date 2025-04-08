package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.services.impl.FileStorageService;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

  private final FileStorageService fileStorageService;

  public FileUploadController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @GetMapping(ConstAPI.FileAPI.FILE + "/{filename}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    try {
      Resource resource = fileStorageService.getFile(filename);
      Path filePath = resource.getFile().toPath();

      // Try to determine content type
      String contentType = Files.probeContentType(filePath);

      // Force ZIP content type if file extension is .zip
      if (filename.toLowerCase().endsWith(".zip")) {
        contentType = "application/zip";
      }

      return ResponseEntity.ok()
          .contentType(
              MediaType.parseMediaType(
                  contentType != null ? contentType : "application/octet-stream"))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
          .body(resource);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(ConstAPI.FileAPI.FILE + "/upload")
  public String getFile(@RequestParam MultipartFile file) {

    return fileStorageService.storeFile(file);
  }


  @GetMapping(ConstAPI.FileAPI.FILE + "/{filename}/size")
  public ResponseEntity<Long> getFileSize(@PathVariable String filename) {
    try {
      Resource resource = fileStorageService.getFile(filename);
      Path filePath = resource.getFile().toPath();
      long fileSize = Files.size(filePath);

      return ResponseEntity.ok(fileSize);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
