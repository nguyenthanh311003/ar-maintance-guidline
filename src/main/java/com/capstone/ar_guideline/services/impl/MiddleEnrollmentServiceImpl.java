package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.Course;
import com.capstone.ar_guideline.services.ICourseService;
import com.capstone.ar_guideline.services.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MiddleEnrollmentServiceImpl {
   @Autowired
   @Lazy
    IEnrollmentService enrollmentService;

    public Integer countByCourseId(String courseId) {
        return enrollmentService.countByCourseId(courseId);
    }

}
