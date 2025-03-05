package com.capstone.ar_guideline.dtos.requests.InstructionDetail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDetailCreationRequest {
  @JsonProperty("instructionId")
  String instructionId;

  @JsonProperty("name")
  String name;

  @JsonProperty("animationName")
  String animationName;

  @JsonProperty("orderNumber")
  Integer orderNumber;

  @JsonProperty("description")
  String description;

  @JsonProperty("meshes")
  List<String> meshes;
}