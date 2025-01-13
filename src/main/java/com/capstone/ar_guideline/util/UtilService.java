package com.capstone.ar_guideline.util;

import java.io.IOException;
import java.util.*;

import com.capstone.ar_guideline.constants.ConstS3Bucket;
import com.capstone.ar_guideline.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UtilService {
//  @Autowired
 // FileStore fileStore;
  public static boolean IsNotNull(Object obj) {
    return obj != null;
  }

  public static boolean IsNotNullOrBlank(String str) {
    return str != null && !str.isBlank();
  }

  public static <T> boolean IsNotNullOrEmptyForSet(Set<T> set) {
    return set != null && !set.isEmpty();
  }

  public static <T> boolean IsNotNullOrEmptyForList(List<T> list) {
    return list != null && !list.isEmpty();
  }

  public static int getTotalPage(int total, int size) {
    return (int) Math.ceil((double) total / size);
  }

  public static void deleteCache(RedisTemplate<String, Object> redisTemplate, Set<String> keys) {
    if (UtilService.IsNotNullOrEmptyForSet(keys)) {
      redisTemplate.delete(keys);
    }
  }

  public String uploadImage(UUID id, MultipartFile file) throws IOException {
    try {
      // 1. Check if image is not empty
      if (file.isEmpty()) {
        throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
      }
      // 2. If file is an image
      if (!Arrays.asList("image/jpeg", "image/png", "image/jpg").contains(file.getContentType())) {
        throw new IllegalStateException("File must be an image [ " + file.getContentType() + "]");
      }
      // 3. The user exists in our database
      // 4. Grab some metadata from file if any
      Map<String, String> metadata = new HashMap<>();
      metadata.put("Content-Type", file.getContentType());
      metadata.put("Content-Length", String.valueOf(file.getSize()));

      // 5. Store the image in S3 and update database (userProfileImageLink) with S3 image link
      String path = String.format("%s/%s", ConstS3Bucket.PROFILE_IMAGE, id);
      String originalFilename = file.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
      String filename = String.format("%s-%s%s", UUID.randomUUID(), originalFilename.substring(0, originalFilename.lastIndexOf('.')), extension);
   //   fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
      return filename;
    } catch (Exception baseException) {
      if (baseException instanceof AppException) {
        throw (AppException) baseException;
      }
      throw baseException;
    }
  }

  public String uploadCvFile(UUID id, MultipartFile file) throws IOException {
    try {
      // 1. Check if file is not empty
      if (file.isEmpty()) {
        throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
      }
      // 2. If file is a PDF
      if (!"application/pdf".equals(file.getContentType())) {
        throw new IllegalStateException("File must be a PDF [ " + file.getContentType() + "]");
      }
      // 4. Grab some metadata from file if any
      Map<String, String> metadata = new HashMap<>();
      metadata.put("Content-Type", file.getContentType());
      metadata.put("Content-Length", String.valueOf(file.getSize()));

      // 5. Store the PDF in S3 and update database (userDocumentLink) with S3 document link
      String path = String.format("%s/%s", ConstS3Bucket.PROFILE_IMAGE, id);
      String originalFilename = file.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
      String filename = String.format("%s-%s%s", UUID.randomUUID(), originalFilename.substring(0, originalFilename.lastIndexOf('.')), extension);
    //  fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());

      return filename;
    } catch (Exception baseException) {
      if (baseException instanceof AppException) {
        throw (AppException) baseException;
      }
      throw baseException;
    }

  }
}
