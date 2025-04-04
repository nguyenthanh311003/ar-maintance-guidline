package com.capstone.ar_guideline.dtos.responses.Model;

import java.io.Serializable;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelResponse implements Serializable {
  String id;
  String modelTypeId;
  String modelTypeName;
  String modelCode;
  String status;
  String name;
  String companyId;
  String description;
  String imageUrl;
  Boolean isUsed;
  String version;
  String scale;
  List<Float> position;
  List<Float> rotation;
  String file;
  String courseName;
}
