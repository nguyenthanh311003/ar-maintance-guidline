package com.capstone.ar_guideline.dtos.requests.Instruction;

import com.capstone.ar_guideline.dtos.requests.InstructionDetail.InstructionDetailCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Vuforia.DatasetRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionCreationRequest {
  String modelId;
  String code;
  Integer orderNumber;
  String name;
  String imageUrlString;
  MultipartFile imageUrl;
  String description;
  DatasetRequest.GuideViewPosition guideViewPosition;
  InstructionDetailCreationRequest instructionDetailRequest;
}
