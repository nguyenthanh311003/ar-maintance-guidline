package com.capstone.ar_guideline.dtos.responses.Machine;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HeaderResponse {
  String keyHeader;
  String valueOfKey;
}
