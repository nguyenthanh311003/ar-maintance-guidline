package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Option.OptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import com.capstone.ar_guideline.entities.Option;
import java.util.List;

public interface IOptionService {
  Option createOption(Option option);

  OptionResponse updateOption(String id, String questionId, OptionCreationRequest request);

  List<Option> findByQuestionId(String questionId);

  void delete(String id);

  Option findById(String id);
}
