package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.InstructionLesson.InstructionLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.InstructionLesson.InstructionLessonResponse;
import com.capstone.ar_guideline.entities.InstructionLesson;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.InstructionLessonMapper;
import com.capstone.ar_guideline.repositories.InstructionLessonRepository;
import com.capstone.ar_guideline.services.IInstructionLessonService;
import com.capstone.ar_guideline.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class InstructionLessonServiceImpl implements IInstructionLessonService {
  private final InstructionLessonRepository instructionLessonRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  private static final String MODEL_LESSON_CACHE_KEY = "model_lesson:all";

  @Override
  public InstructionLessonResponse create(InstructionLessonCreationRequest request) {
    try {
      // Map request to entity
      InstructionLesson newInstructionLesson = InstructionLessonMapper.toInstructionLesson(request);
      InstructionLesson savedInstructionLesson =
          instructionLessonRepository.save(newInstructionLesson);

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(MODEL_LESSON_CACHE_KEY));

      return InstructionLessonMapper.toInstructionLessonResponse(savedInstructionLesson);
    } catch (Exception e) {
      log.error("Failed to create InstructionLesson: {}", e.getMessage(), e);
      throw new AppException(ErrorCode.MODEL_LESSON_CREATE_FAILED);
    }
  }

  @Override
  public InstructionLessonResponse update(String id, InstructionLessonCreationRequest request) {
    try {
      // Find existing model lesson
      InstructionLesson existingInstructionLesson = findById(id);

      // Update entity with new data
      InstructionLesson updatedInstructionLesson =
          InstructionLessonMapper.toInstructionLesson(request);
      InstructionLesson savedInstructionLesson =
          instructionLessonRepository.save(updatedInstructionLesson);

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(MODEL_LESSON_CACHE_KEY));

      return InstructionLessonMapper.toInstructionLessonResponse(savedInstructionLesson);
    } catch (Exception e) {
      log.error("Failed to update InstructionLesson with ID {}: {}", id, e.getMessage(), e);
      throw new AppException(ErrorCode.MODEL_LESSON_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      // Find existing model lesson
      InstructionLesson existingInstructionLesson = findById(id);

      // Delete entity
      instructionLessonRepository.deleteById(existingInstructionLesson.getId());

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(MODEL_LESSON_CACHE_KEY));
    } catch (Exception e) {
      log.error("Failed to delete InstructionLesson with ID {}: {}", id, e.getMessage(), e);
      throw new AppException(ErrorCode.MODEL_LESSON_DELETE_FAILED);
    }
  }

  @Override
  public InstructionLesson findById(String id) {
    return instructionLessonRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("InstructionLesson with ID {} not found", id);
              return new AppException(ErrorCode.MODEL_LESSON_NOT_EXISTED);
            });
  }
}
