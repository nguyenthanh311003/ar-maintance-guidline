package com.capstone.ar_guideline.dtos.responses.Model;

import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
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
  String modelCode;
  String status;
  String name;
  String description;
  String imageUrl;
  String version;
  String scale;
  String file;
  String fileDat;
  List<InstructionResponse> instructionResponses;
}
