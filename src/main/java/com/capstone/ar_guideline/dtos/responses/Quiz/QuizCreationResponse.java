package com.capstone.ar_guideline.dtos.responses.Quiz;

import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizCreationResponse {
  String id;
  String courseId;
  String title;
  String description;
  QuestionResponse questionResponse;
  List<OptionResponse> optionResponses;
}
