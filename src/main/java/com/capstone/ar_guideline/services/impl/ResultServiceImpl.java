package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Result.ResultCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Result.ResultResponse;
import com.capstone.ar_guideline.entities.Quiz;
import com.capstone.ar_guideline.entities.Result;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.ResultMapper;
import com.capstone.ar_guideline.repositories.ResultRepository;
import com.capstone.ar_guideline.services.IQuizService;
import com.capstone.ar_guideline.services.IResultService;
import com.capstone.ar_guideline.services.IUserService;
import com.capstone.ar_guideline.util.UtilService;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ResultServiceImpl implements IResultService {
  ResultRepository resultRepository;
  RedisTemplate<String, Object> redisTemplate;
  IUserService userService;
  IQuizService quizService;

  private final String[] keysToRemove = {ConstHashKey.HASH_KEY_RESULT};

  @Override
  public ResultResponse create(ResultCreationRequest request) {
    try {
      User userById = userService.findById(request.getUseId());

      Quiz quizById = quizService.findById(request.getQuizId());

      Result newResult =
          ResultMapper.fromResultCreationRequestToEntity(request, quizById, userById);

      newResult = resultRepository.save(newResult);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return ResultMapper.fromEntityToResultResponse(newResult);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.RESULT_CREATE_FAILED);
    }
  }

  @Override
  public ResultResponse update(String id, ResultCreationRequest request) {
    try {
      Result resultById = findById(id);

      User userById = userService.findById(request.getUseId());

      Quiz quizById = quizService.findById(request.getQuizId());

      resultById.setUser(userById);
      resultById.setQuiz(quizById);

      resultById = resultRepository.save(resultById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return ResultMapper.fromEntityToResultResponse(resultById);

    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.RESULT_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Result resultById = findById(id);

      resultRepository.deleteById(resultById.getId());
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.RESULT_DELETE_FAILED);
    }
  }

  @Override
  public Result findById(String id) {
    try {
      return resultRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.RESULT_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.RESULT_NOT_EXISTED);
    }
  }
}
