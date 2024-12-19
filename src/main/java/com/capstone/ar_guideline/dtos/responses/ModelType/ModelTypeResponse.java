package com.capstone.ar_guideline.dtos.responses.ModelType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelTypeResponse {
     String id;
     String name;
     String image;
     String description;
}
