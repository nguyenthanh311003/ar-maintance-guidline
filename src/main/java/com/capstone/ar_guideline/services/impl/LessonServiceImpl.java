package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.Lesson.LessonCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.LessonMapper;
import com.capstone.ar_guideline.repositories.LessonRepository;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.ILessonDetailService;
import com.capstone.ar_guideline.services.ILessonService;
import com.capstone.ar_guideline.util.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements ILessonService {

  private final LessonRepository lessonRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  @Autowired
  @Lazy
  private ILessonDetailService lessonDetailService;

  @Autowired
  @Lazy
  private ICourseService courseService;


  @Override
  public LessonResponse create(LessonCreationRequest request) {
    try {
      courseService.findById(request.getCourseId());
      Integer orderInCourse = lessonRepository.countByCourseId(request.getCourseId());

      if (orderInCourse == 0) {
        orderInCourse = 1;
      } else {
        orderInCourse++;
      }

      request.setDuration(0);

      request.setOrderInCourse(orderInCourse);
      Lesson newLesson = LessonMapper.fromLessonCreationRequestToEntity(request);
      Lesson savedLesson = lessonRepository.save(newLesson);

      return LessonMapper.FromEntityToLessonResponse(savedLesson);
    } catch (Exception e) {
      log.error("Failed to create lesson: {}", e.getMessage(), e);
      throw new AppException(ErrorCode.LESSON_CREATE_FAILED);
    }
  }

  @Override
  public LessonResponse update(String id, LessonCreationRequest request) {
    try {
      Lesson updatedLesson = LessonMapper.fromLessonCreationRequestToEntity(request);

      Lesson savedLesson = lessonRepository.save(updatedLesson);

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

    } catch (Exception e) {
      log.error("Failed to delete lesson with ID {}: {}", id, e.getMessage(), e);
      throw new AppException(ErrorCode.LESSON_DELETE_FAILED);
    }
  }

  @Override
  public List<LessonResponse> findByCourseId(String courseId) {
    try {
      courseService.findById(courseId);
      List<Lesson> lessons = lessonRepository.findAllByCourseId(courseId);
      List<LessonResponse> lessonResponses = new ArrayList<>();
      for (Lesson lesson : lessons) {
        LessonResponse lessonResponse = LessonMapper.FromEntityToLessonResponse(lesson);
        lessonResponse.setLessonDetails(lessonDetailService.findAllByLessonId(lesson.getId()));
        lessonResponses.add(lessonResponse);
      }
      lessonResponses.sort(Comparator.comparing(LessonResponse::getOrderInCourse));
       return lessonResponses;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
    }
   return null;
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

  @Override
  public Integer countByCourseId(String courseId) {
    return lessonRepository.countByCourseId(courseId);
  }

  @Override
  public void updateDuration(String lessonId, Integer duration) {
    try {
      Lesson lesson = findById(lessonId);
      lesson.setDuration(duration);
      lessonRepository.save(lesson);
    } catch (Exception e) {
      log.error("Failed to update lesson duration with ID {}: {}", lessonId, e.getMessage(), e);
      throw new AppException(ErrorCode.LESSON_UPDATE_FAILED);
    }
  }
}
