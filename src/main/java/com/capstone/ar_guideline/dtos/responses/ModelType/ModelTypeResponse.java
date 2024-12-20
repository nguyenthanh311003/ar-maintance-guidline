package com.capstone.ar_guideline.dtos.responses.ModelType;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelTypeResponse implements Serializable{
     String id;
     String name;
     String image;
     String description;
}
