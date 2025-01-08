package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.LessonMapper;
import com.capstone.ar_guideline.repositories.LessonRepository;
import com.capstone.ar_guideline.services.ILessonService;
import com.capstone.ar_guideline.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements ILessonService {

  private final LessonRepository lessonRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  private static final String LESSON_CACHE_KEY = "lesson:all";

  @Override
  public LessonResponse create(LessonCreationRequest request) {
    try {
      Lesson newLesson = LessonMapper.fromLessonCreationRequestToEntity(request);
      Lesson savedLesson = lessonRepository.save(newLesson);

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(LESSON_CACHE_KEY));

      return LessonMapper.FromEntityToLessonResponse(savedLesson);
    } catch (Exception e) {
      log.error("Failed to create lesson: {}", e.getMessage(), e);
      throw new AppException(ErrorCode.LESSON_CREATE_FAILED);
    }
  }

  @Override
  public LessonResponse update(String id, LessonCreationRequest request) {
    try {
      Lesson existingLesson = findById(id);
      Lesson updatedLesson = LessonMapper.fromLessonCreationRequestToEntity(request);

      Lesson savedLesson = lessonRepository.save(updatedLesson);

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(LESSON_CACHE_KEY));

      return LessonMapper.FromEntityToLessonResponse(savedLesson);
    } catch (Exception e) {
      log.error("Failed to update lesson with ID {}: {}", id, e.getMessage(), e);
      throw new AppException(ErrorCode.LESSON_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      Lesson existingLesson = findById(id);
      lessonRepository.deleteById(existingLesson.getId());

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(LESSON_CACHE_KEY));
    } catch (Exception e) {
      log.error("Failed to delete lesson with ID {}: {}", id, e.getMessage(), e);
      throw new AppException(ErrorCode.LESSON_DELETE_FAILED);
    }
  }

  @Override
  public Lesson findById(String id) {
    return lessonRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("Lesson with ID {} not found", id);
              return new AppException(ErrorCode.LESSON_NOT_EXISTED);
            });
  }
}
