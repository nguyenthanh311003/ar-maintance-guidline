package com.capstone.ar_guideline.dtos.requests.Model;

import com.capstone.ar_guideline.dtos.requests.Vuforia.DatasetRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelCreationRequest {
  String modelTypeId;
  String modelCode;
  String status;
  String name;
  Boolean isUsed;
  String companyId;
  String description;
  MultipartFile imageUrl;
  String version;
  String scale;
  List<Float> position;
  List<Float> rotation;
  MultipartFile file;
}
