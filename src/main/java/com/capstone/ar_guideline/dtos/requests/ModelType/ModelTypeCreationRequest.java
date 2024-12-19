package com.capstone.ar_guideline.dtos.requests.ModelType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelTypeCreationRequest {
     String name;
     String image;
     String description;
}
