package com.hs.course.aspect;

import com.hs.course.daogenerator.RecordAnswerGeneratorMapper;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.RecordAnswerGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 答题日志记录
 */
@Aspect
@Component
public class AnswerAspect {
    @Autowired
    private RecordAnswerGeneratorMapper recordAnswerGeneratorMapper;
    @Autowired
    private UserGeneratorMapper userGeneratorMapper;

//    @Around("execution(* com.hs.course.controller.CourseController.choiceAnswerJudge(..))")
//    public Object answerRecord(ProceedingJoinPoint p){
//        Object[] args = p.getArgs();
//
//
//
//        return 1;
//    }


}
