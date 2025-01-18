package com.capstone.ar_guideline.dtos.requests.Quiz;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizCreationRequest {

    private String courseId;
    private String title;
    private String description;

}