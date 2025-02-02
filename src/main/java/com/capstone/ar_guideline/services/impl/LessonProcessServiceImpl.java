package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.LessonProcess.LessonProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.Lesson.LessonResponse;
import com.capstone.ar_guideline.dtos.responses.LessonDetail.LessonDetailResponse;
import com.capstone.ar_guideline.dtos.responses.LessonProcess.LessonProcessResponse;
import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.entities.LessonProcess;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.LessonProcessMapper;
import com.capstone.ar_guideline.repositories.LessonProcessRepository;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.ILessonDetailService;
import com.capstone.ar_guideline.services.ILessonProcessService;
import com.capstone.ar_guideline.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonProcessServiceImpl implements ILessonProcessService {

  private final LessonProcessRepository lessonProcessRepository;
  @Autowired
  private ILessonDetailService lessonDetailService;

  @Autowired
  private ICourseService courseService;

  @Autowired
  private IUserService userService;
  @Override
  public LessonProcessResponse create(LessonProcessCreationRequest request) {
    try {

      lessonDetailService.findById(request.getLessonDetailId());
      userService.findById(request.getUserId());

      LessonProcess newLessonProcess = LessonProcessMapper.fromLessonProcessCreationRequestToEntity(request);

      // Save the new LessonProcess to the database
      newLessonProcess = lessonProcessRepository.save(newLessonProcess);

      // Return the created LessonProcess as LessonProcessResponse
      return LessonProcessMapper.fromEntityToLessonProcessResponse(newLessonProcess);
    } catch (Exception exception) {
      throw new AppException(ErrorCode.LESSON_PROCESS_CREATE_FAILED);
    }
  }

  @Override
  public LessonProcessResponse update(String id, LessonProcessCreationRequest request) {
    try {
      // Find the existing LessonProcess by id
      LessonProcess existingLessonProcess =
          lessonProcessRepository
              .findById(id)
              .orElseThrow(() -> new AppException(ErrorCode.LESSON_PROCESS_NOT_EXISTED));

      // Update the LessonProcess fields with the request data
      existingLessonProcess = LessonProcessMapper.fromLessonProcessCreationRequestToEntity(request);

      // Save the updated LessonProcess to the database
      existingLessonProcess = lessonProcessRepository.save(existingLessonProcess);

      // Return the updated LessonProcess as LessonProcessResponse
      return LessonProcessMapper.fromEntityToLessonProcessResponse(existingLessonProcess);
    } catch (Exception exception) {
      throw new AppException(ErrorCode.LESSON_PROCESS_UPDATE_FAILED);
    }
  }

  @Override
  public void delete(String id) {
    try {
      // Find the existing LessonProcess by id
      LessonProcess existingLessonProcess =
          lessonProcessRepository
              .findById(id)
              .orElseThrow(() -> new AppException(ErrorCode.LESSON_PROCESS_NOT_EXISTED));

      // Delete the LessonProcess from the database
      lessonProcessRepository.delete(existingLessonProcess);
    } catch (Exception exception) {
      throw new AppException(ErrorCode.LESSON_PROCESS_DELETE_FAILED);
    }
  }

  @Override
  public LessonProcess findById(String id) {
    try {
      // Find the LessonProcess by id
      return lessonProcessRepository
          .findById(id)
          .orElseThrow(() -> new AppException(ErrorCode.LESSON_PROCESS_NOT_EXISTED));
    } catch (Exception exception) {
      throw new AppException(ErrorCode.LESSON_PROCESS_NOT_EXISTED);
    }
  }

  @Override
  public void createAll(String courseId, String userId) {
      CourseResponse course = courseService.findByIdResponse(courseId);
      List<LessonResponse> lessons = course.getLessons();
        for (LessonResponse lesson : lessons) {
         for (LessonDetailResponse lessonDetail : lesson.getLessonDetails()) {
           LessonProcessCreationRequest request = new LessonProcessCreationRequest();
           request.setLessonDetailId(lessonDetail.getId());
           request.setUserId(userId);
           create(request);
        }
  }
    }
}
