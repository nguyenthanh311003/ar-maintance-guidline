package com.capstone.ar_guideline.dtos.requests.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String description;
    String image;
    String documentUrl;
    String aRUrl;
    String version;
    String rotation;
    String scale;
    String fileType;
    Long fileSize;
}
