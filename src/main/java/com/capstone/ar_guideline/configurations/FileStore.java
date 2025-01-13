//package com.capstone.ar_guideline.configurations;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.*;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.util.IOUtils;
//import com.capstone.ar_guideline.exceptions.AppException;
//import com.capstone.ar_guideline.exceptions.ErrorCode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.InputStream;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class FileStore {
//    private final AmazonS3 s3;
//
//    @Autowired
//    public FileStore(AmazonS3 s3) {
//        this.s3 = s3;
//    }
//
//    //    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetedata, InputStream inputStream) throws BaseException {
////
////        ObjectMetadata metadata = new ObjectMetadata();
////        optionalMetedata.ifPresent(map -> {
////            if (!map.isEmpty()) {
////                map.forEach(metadata::addUserMetadata);
////            }
////        });
////        try {
////            PutObjectRequest putObjectRequest = new PutObjectRequest(BucketName.PROFILE_IMAGE.getBucketName(), fileName, inputStream, metadata)
////                    .withCannedAcl(CannedAccessControlList.PublicRead);
////
////            s3.putObject(putObjectRequest);
//////            s3.putObject("tortee-image-upload", fileName, inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
////        } catch (AmazonServiceException e) {
////throw new BaseException(ErrorCode.ERROR_500.getCode(), "Failed to store file to s3", ErrorCode.ERROR_500.getMessage());
////        }
////
////    }
//    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) throws AppException {
//        ObjectMetadata metadata = new ObjectMetadata();
//        optionalMetadata.ifPresent(map -> {
//            if (!map.isEmpty()) {
//                map.forEach((key, value) -> {
//                    if (key.equalsIgnoreCase("Content-Type")) {
//                        metadata.setContentType(value);
//                    } else if (key.equalsIgnoreCase("Content-Length")) {
//                        metadata.setContentLength(Long.parseLong(value));
//                    } else {
//                        metadata.addUserMetadata(key, value);
//                    }
//                });
//            }
//        });
//
//        try {
//            PutObjectRequest putObjectRequest = new PutObjectRequest("tortee-file-storage", fileName, inputStream, metadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead);
//
//            s3.putObject(putObjectRequest);
//        } catch (AmazonServiceException e) {
//            throw new AppException(ErrorCode.AWS_S3_BUCKET_FAILED_TO_STORE_FILE);
//        }
//    }
//
//    public byte[] download(String path, String key) {
//        try {
//            S3Object object = s3.getObject(path, key);
//            S3ObjectInputStream inputStream = object.getObjectContent();
//            return IOUtils.toByteArray(object.getObjectContent());
//        } catch (AmazonServiceException | java.io.IOException e) {
//            throw new IllegalStateException("Failed to download file to s3", e);
//        }
//    }
//}
