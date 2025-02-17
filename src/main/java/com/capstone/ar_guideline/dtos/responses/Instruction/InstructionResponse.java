package com.capstone.ar_guideline.dtos.responses.Instruction;

import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionResponse {
  String id;
  String modelId;
  String code;
  Integer orderNumber;
  String imageUrl;
  String name;
  String description;
  String position;
  String rotation;
  List<InstructionDetailResponse> instructionDetailResponse;
}
