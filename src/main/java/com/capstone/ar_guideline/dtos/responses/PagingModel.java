package com.capstone.ar_guideline.dtos.responses;

import java.util.List;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PagingModel<T> {
  private int page;
  private int size;
  private int totalItems;
  private int totalPages;
  private List<T> object;
}
