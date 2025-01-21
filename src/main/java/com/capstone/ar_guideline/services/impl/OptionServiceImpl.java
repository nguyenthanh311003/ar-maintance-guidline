package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Option.OptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import com.capstone.ar_guideline.entities.Option;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.OptionMapper;
import com.capstone.ar_guideline.repositories.OptionRepository;
import com.capstone.ar_guideline.services.IOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements IOptionService {

  @Autowired OptionRepository optionRepository;

  @Override
  public OptionResponse createOption(OptionCreationRequest optionCreationRequest) {

    try {

      Option option = OptionMapper.fromOptionCreationRequestToEntity(optionCreationRequest);

      option = optionRepository.save(option);

      return OptionMapper.fromEntityToOptionResponse(option);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUIZ_CREATE_FAILED);
    }
  }
}
