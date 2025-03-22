package com.capstone.ar_guideline.dtos.responses.ModelType;

import com.capstone.ar_guideline.dtos.responses.Company.CompanyResponse;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelTypeResponse implements Serializable {
  String id;
  String name;
  String image;
  String description;
  CompanyResponse company;
}
