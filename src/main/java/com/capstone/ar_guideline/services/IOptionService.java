package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Option.OptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;

public interface IOptionService {
  OptionResponse createOption(OptionCreationRequest optionCreationRequest);
}
