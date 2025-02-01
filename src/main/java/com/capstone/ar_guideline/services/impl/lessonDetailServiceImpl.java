package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.dtos.requests.LessonDetail.LessonDetailCreationRequest;
import com.capstone.ar_guideline.dtos.requests.LessonProcess.LessonProcessCreationRequest;
import com.capstone.ar_guideline.dtos.responses.LessonDetail.LessonDetailResponse;
import com.capstone.ar_guideline.entities.Lesson;
import com.capstone.ar_guideline.entities.LessonDetail;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.LessonDetailMapper;
import com.capstone.ar_guideline.mappers.LessonMapper;
import com.capstone.ar_guideline.repositories.LessonDetailRepository;
import com.capstone.ar_guideline.services.ILessonDetailService;
import com.capstone.ar_guideline.services.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class lessonDetailServiceImpl implements ILessonDetailService {

    @Autowired
    private LessonDetailRepository lessonDetailRepository;

    @Autowired
    private ILessonService lessonService;

    @Override
    public LessonDetailResponse create(LessonDetailCreationRequest lessonProcessCreationRequest) {
      try {
          Lesson lesson = lessonService.findById(lessonProcessCreationRequest.getLessonId());
          Integer orderInLesson = lessonDetailRepository.findLessonDetailsByLessonId(lessonProcessCreationRequest.getLessonId()).size();
          if(orderInLesson == 0) {
              orderInLesson = 1;
          } else {
              orderInLesson++;
          }
         lessonProcessCreationRequest.setOrderInLesson(orderInLesson);
          LessonDetail lessonDetail = LessonDetailMapper.fromLessonDetailCreationRequestToEntity(lessonProcessCreationRequest);
          lessonDetail = lessonDetailRepository.save(lessonDetail);
          Integer lessonDuration = lesson.getDuration()+lessonProcessCreationRequest.getDuration();
          lessonService.updateDuration(lesson.getId(), lessonDuration);
          return LessonDetailMapper.fromEntityToLessonDetailResponse(lessonDetail);
      }catch (Exception exception) {
        throw new AppException(ErrorCode.LESSON_DETAIL_CREATE_FAILED);
      }

    }

    @Override
    public LessonDetailResponse update(String id, LessonDetailCreationRequest lessonProcessCreationRequest) {
        try {
            findById(id);
            lessonService.findById(lessonProcessCreationRequest.getLessonId());
            LessonDetail lessonDetail = LessonDetailMapper.fromLessonDetailCreationRequestToEntity(lessonProcessCreationRequest);
            lessonDetail = lessonDetailRepository.save(lessonDetail);
            return LessonDetailMapper.fromEntityToLessonDetailResponse(lessonDetail);
        }catch (Exception exception) {
            throw new AppException(ErrorCode.LESSON_DETAIL_UPDATE_FAILED);
        }
    }

    @Override
    public LessonDetail findById(String id) {
        return lessonDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_PROCESS_NOT_EXISTED));    }

    @Override
    public void delete(String id) {
       LessonDetail lessonDetail = findById(id);
        lessonDetailRepository.delete(lessonDetail);
    }

    @Override
    public List<LessonDetailResponse> findAllByLessonId(String lessonId) {
        List<LessonDetail> lessonDetails = lessonDetailRepository.findLessonDetailsByLessonId(lessonId);
        List<LessonDetailResponse> lessonDetailResponses = new ArrayList<>();
        for (LessonDetail lessonDetail : lessonDetails) {
            LessonDetailResponse lessonDetailResponse = new LessonDetailResponse();
            lessonDetailResponse = LessonDetailMapper.fromEntityToLessonDetailResponse(lessonDetail);
            lessonDetailResponses.add(lessonDetailResponse);

        }
        lessonDetailResponses.sort(Comparator.comparing(LessonDetailResponse::getOrderInLesson));
        return lessonDetailResponses;
    }
}
