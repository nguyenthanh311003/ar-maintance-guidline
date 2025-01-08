package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.ModelLesson.ModelLessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.ModelLesson.ModelLessonResponse;
import com.capstone.ar_guideline.entities.ModelLesson;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.ModelLessonMapper;
import com.capstone.ar_guideline.repositories.ModelLessonRepository;
import com.capstone.ar_guideline.services.IModelLessonService;
import com.capstone.ar_guideline.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ModelLessonServiceImpl implements IModelLessonService {
  private final ModelLessonRepository modelLessonRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  private static final String MODEL_LESSON_CACHE_KEY = "model_lesson:all";

  @Override
  public ModelLessonResponse create(ModelLessonCreationRequest request) {
    try {
      // Map request to entity
      ModelLesson newModelLesson = ModelLessonMapper.toModelLesson(request);
      ModelLesson savedModelLesson = modelLessonRepository.save(newModelLesson);

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(MODEL_LESSON_CACHE_KEY));

      return ModelLessonMapper.toModelLessonResponse(savedModelLesson);
    } catch (Exception e) {
      log.error("Failed to create ModelLesson: {}", e.getMessage(), e);
      throw new AppException(ErrorCode.MODEL_LESSON_CREATE_FAILED);
    }
  }

  @Override
  public ModelLessonResponse update(String id, ModelLessonCreationRequest request) {
    try {
      // Find existing model lesson
      ModelLesson existingModelLesson = findById(id);

      // Update entity with new data
      ModelLesson updatedModelLesson = ModelLessonMapper.toModelLesson(request);
      ModelLesson savedModelLesson = modelLessonRepository.save(updatedModelLesson);

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(MODEL_LESSON_CACHE_KEY));

      return ModelLessonMapper.toModelLessonResponse(savedModelLesson);
    } catch (Exception e) {
      log.error("Failed to update ModelLesson with ID {}: {}", id, e.getMessage(), e);
      throw new AppException(ErrorCode.MODEL_LESSON_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      // Find existing model lesson
      ModelLesson existingModelLesson = findById(id);

      // Delete entity
      modelLessonRepository.deleteById(existingModelLesson.getId());

      // Invalidate cache
      UtilService.deleteCache(redisTemplate, redisTemplate.keys(MODEL_LESSON_CACHE_KEY));
    } catch (Exception e) {
      log.error("Failed to delete ModelLesson with ID {}: {}", id, e.getMessage(), e);
      throw new AppException(ErrorCode.MODEL_LESSON_DELETE_FAILED);
    }
  }

  @Override
  public ModelLesson findById(String id) {
    return modelLessonRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("ModelLesson with ID {} not found", id);
              return new AppException(ErrorCode.MODEL_LESSON_NOT_EXISTED);
            });
  }
}
