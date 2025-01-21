package com.capstone.ar_guideline.dtos.requests.Question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreationRequest {
  String quizId;

  String question;
}
