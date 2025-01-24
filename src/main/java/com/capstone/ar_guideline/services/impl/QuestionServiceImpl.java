package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Question.QuestionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Question.QuestionModifyRequest;
import com.capstone.ar_guideline.dtos.responses.Option.OptionResponse;
import com.capstone.ar_guideline.dtos.responses.Question.QuestionResponse;
import com.capstone.ar_guideline.entities.Option;
import com.capstone.ar_guideline.entities.Question;
import com.capstone.ar_guideline.entities.Quiz;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.OptionMapper;
import com.capstone.ar_guideline.mappers.QuestionMapper;
import com.capstone.ar_guideline.repositories.QuestionRepository;
import com.capstone.ar_guideline.services.IOptionService;
import com.capstone.ar_guideline.services.IQuestionService;
import com.capstone.ar_guideline.services.IQuizService;
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
public class QuestionServiceImpl implements IQuestionService {
  QuestionRepository questionRepository;
  IQuizService quizService;
  IOptionService optionService;

  @Override
  public QuestionResponse createQuestion(QuestionCreationRequest request) {
    try {
      Quiz quizById = quizService.findById(request.getQuizId());

      Question newQuestion = QuestionMapper.fromQuestionCreationRequestToEntity(request, quizById);

      newQuestion = questionRepository.save(newQuestion);

      Question finalNewQuestion = newQuestion;

      List<OptionResponse> optionResponses =
          request.getOptionCreationRequests().stream()
              .map(
                  ocr -> {
                    Option newOption =
                        OptionMapper.fromOptionCreationRequestToEntity(ocr, finalNewQuestion);
                    newOption = optionService.createOption(newOption);

                    return OptionMapper.fromEntityToOptionResponse(newOption);
                  })
              .toList();

      return QuestionMapper.fromEntityAndOptionResponsesToQuestionResponse(
          newQuestion, optionResponses);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUESTION_CREATE_FAILED);
    }
  }

  @Override
  public QuestionResponse update(String id, QuestionModifyRequest request) {
    try {
      Question questionById = findById(id);

      questionById.setQuestion(request.getQuestion());

      questionById = questionRepository.save(questionById);

      Question finalQuestionById = questionById;
      List<OptionResponse> optionResponses =
          request.getOptionCreationRequests().stream()
              .map(ocr -> optionService.updateOption(ocr.getId(), finalQuestionById.getId(), ocr))
              .toList();

      return QuestionMapper.fromEntityAndOptionResponsesToQuestionResponse(
          questionById, optionResponses);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUESTION_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Question questionById = findById(id);

      List<Option> options = optionService.findByQuestionId(questionById.getId());
      options.forEach(
          option -> optionService.delete(option.getId()));

      questionRepository.deleteById(questionById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUESTION_DELETE_FAILED);
    }
  }

  @Override
  public Question findById(String id) {
    try {
      return questionRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUESTION_NOT_EXISTED);
    }
  }
}
