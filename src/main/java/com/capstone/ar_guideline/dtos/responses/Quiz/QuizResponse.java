package com.capstone.ar_guideline.dtos.responses.Quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor@Builder

public class QuizResponse {

    private String courseId;
    private String title;
    private String description;

}
