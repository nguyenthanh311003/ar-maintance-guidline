package com.capstone.ar_guideline.dtos.responses.Question;

import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionResponse {
  String id;
  String quizId;
  String question;
  List<OptionResponse> optionResponses;
}
