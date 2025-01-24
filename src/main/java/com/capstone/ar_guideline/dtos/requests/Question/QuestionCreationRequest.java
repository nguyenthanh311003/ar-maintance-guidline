package com.capstone.ar_guideline.dtos.requests.Question;

import com.capstone.ar_guideline.dtos.requests.Option.OptionCreationRequest;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionCreationRequest {
  String quizId;
  String question;
  List<OptionCreationRequest> optionCreationRequests;
}
