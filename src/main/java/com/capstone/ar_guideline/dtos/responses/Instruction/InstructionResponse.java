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
  String courseId;
  Integer orderNumber;
  String name;
  String description;
  List<InstructionDetailResponse> instructionDetailResponse;
}
