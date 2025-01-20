package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstHashKey;
import com.capstone.ar_guideline.dtos.requests.Quiz.QuizCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Quiz.QuizResponse;
import com.capstone.ar_guideline.entities.Quiz;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.QuizMapper;
import com.capstone.ar_guideline.repositories.QuizRepository;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.IQuizService;
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
public class QuizServiceImpl implements IQuizService {
  QuizRepository quizRepository;
  RedisTemplate<String, Object> redisTemplate;
  ICourseService courseService;

  private final String[] keysToRemove = {ConstHashKey.HASH_KEY_QUIZ};

  @Override
  public QuizResponse create(QuizCreationRequest request) {
    try {
      courseService.findById(request.getCourseId());

      Quiz newQuiz = QuizMapper.fromQuizCreationRequestToEntity(request);

      newQuiz = quizRepository.save(newQuiz);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return QuizMapper.fromEntityToQuizResponse(newQuiz);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUIZ_CREATE_FAILED);
    }
  }

  @Override
  public QuizResponse update(String id, QuizCreationRequest request) {
    try {
      Quiz quizById = findById(id);

      courseService.findById(request.getCourseId());

      quizById.setCourseId(request.getCourseId());
      quizById.setTitle(request.getTitle());
      quizById.setDescription(request.getDescription());

      quizById = quizRepository.save(quizById);

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      return QuizMapper.fromEntityToQuizResponse(quizById);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUIZ_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Quiz quizById = findById(id);
      quizRepository.deleteById(quizById.getId());

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      Arrays.stream(keysToRemove)
          .map(k -> k + ConstHashKey.HASH_KEY_OBJECT)
          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUIZ_DELETE_FAILED);
    }
  }

  @Override
  public Quiz findById(String id) {
    try {
      return quizRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_EXISTED));
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.QUIZ_NOT_EXISTED);
    }
  }
}
