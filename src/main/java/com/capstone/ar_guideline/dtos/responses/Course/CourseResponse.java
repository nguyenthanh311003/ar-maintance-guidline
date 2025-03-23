package com.capstone.ar_guideline.dtos.responses.Course;

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
public class CourseResponse implements Serializable {
  String id;
  String companyId;
  String modelId;
  List<InstructionResponse> instructions;
  String title;
  String description;
  String shortDescription;
  String targetAudience;
  Integer duration;
  Boolean isMandatory;
  String imageUrl;
  String qrCode;
  String courseCode;
  Integer numberOfLessons;
  Integer numberOfParticipants;
  Integer numberOfScan;
  String status;
  String type;
  String machineTypeId;
}
