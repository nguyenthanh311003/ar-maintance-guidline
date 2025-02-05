package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Option.OptionCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import com.capstone.ar_guideline.entities.Option;
import com.capstone.ar_guideline.entities.Question;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.OptionMapper;
import com.capstone.ar_guideline.repositories.OptionRepository;
import com.capstone.ar_guideline.repositories.QuestionRepository;
import com.capstone.ar_guideline.services.IOptionService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OptionServiceImpl implements IOptionService {
  OptionRepository optionRepository;
  QuestionRepository questionRepository;

  @Override
  public Option createOption(Option option) {
    try {
      return optionRepository.save(option);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.OPTION_CREATE_FAILED);
    }
  }

  @Override
  public OptionResponse updateOption(String id, String questionId, OptionCreationRequest request) {
    try {
      Option optionById = findById(id);

      Question questionById =
          questionRepository
              .findById(questionId)
              .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_EXISTED));

      optionById.setOption(request.getOption());
      optionById.setQuestion(questionById);
      optionById.setIsRight(request.getIsRight());

      optionById = optionRepository.save(optionById);

      return OptionMapper.fromEntityToOptionResponse(optionById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.OPTION_UPDATE_FAILED);
    }
  }

  @Override
  public List<Option> findByQuestionId(String questionId) {
    try {
      return optionRepository.findByQuestionId(questionId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.OPTION_NOT_EXISTED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Option optionById = findById(id);
      optionRepository.deleteById(optionById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.OPTION_DELETE_FAILED);
    }
  }

  @Override
  public Option findById(String id) {
    try {
      return optionRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.OPTION_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.OPTION_NOT_EXISTED);
    }
  }
}
