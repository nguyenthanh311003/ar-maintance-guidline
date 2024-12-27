package com.capstone.ar_guideline.dtos.responses.LessonProcess;

import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonProcessResponse implements Serializable {
    String id;
    String lessonId;
    String userId;
    Boolean isCompleted;
    String completeDate;
}
