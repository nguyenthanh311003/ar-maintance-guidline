package com.capstone.ar_guideline.dtos.responses.InstructionDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDetailResponse {
  String id;
  String instructionId;
  String name;
  String animationName;
  List<String> meshes;
  Integer orderNumber;
  String description;
}
