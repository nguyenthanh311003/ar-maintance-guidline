package com.capstone.ar_guideline.dtos.requests.LessonDetail;

import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.entities.LessonProcess;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDetailCreationRequest {

    private String lessonId;
    private String title;
    private Integer orderInLesson;
    private String description;
    private Integer duration;
    private String status;
    private String videoUrl;
    private String type;

}
