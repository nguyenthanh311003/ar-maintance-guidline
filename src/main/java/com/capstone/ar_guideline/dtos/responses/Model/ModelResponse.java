package com.capstone.ar_guideline.dtos.responses.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelResponse implements Serializable{
     String id;
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