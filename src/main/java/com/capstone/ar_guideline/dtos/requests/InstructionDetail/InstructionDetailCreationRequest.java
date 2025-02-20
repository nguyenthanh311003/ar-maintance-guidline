package com.capstone.ar_guideline.dtos.requests.InstructionDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDetailCreationRequest {
  String instructionId;
  String name;
  Integer orderNumber;
  String description;
  String imageString;
  String fileString;
  MultipartFile imageFile;
  MultipartFile multipartFile;
}
