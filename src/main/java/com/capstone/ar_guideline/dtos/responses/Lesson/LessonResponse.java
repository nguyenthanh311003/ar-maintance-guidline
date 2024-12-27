package com.capstone.ar_guideline.dtos.responses.Lesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse implements Serializable {
    String id;
    String courseId;
    String title;
    Integer orderInCourse;
    String description;
    Integer duration;
    String status;
}
