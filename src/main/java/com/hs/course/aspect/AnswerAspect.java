package com.hs.course.aspect;

import com.hs.course.daogenerator.RecordAnswerGeneratorMapper;
import com.hs.course.daogenerator.UserGeneratorMapper;
import com.hs.course.domaingenerator.RecordAnswerGenerator;
import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.domaingenerator.UserGeneratorExample;
import com.hs.course.entity.User;
import com.hs.course.utils.DataConversion;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

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
    private static Logger logger = LoggerFactory.getLogger(AnswerAspect.class);

    public User getuser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }

    @Around("execution(* com.hs.course.controller.CourseController.choiceAnswerJudge(..)) || " +
            "execution(* com.hs.course.controller.SummaryController.summaryJudge(..))")
    public Object answerRecord(ProceedingJoinPoint p) {
        RecordAnswerGenerator recordAnswer = null;
        long now = 0;
        try {
            Object[] args = p.getArgs();
            Integer id = (Integer) args[0];
            String answer = (String) args[1];
            User user = getuser();
            String userName = user.getName();
            UserGeneratorExample ex = new UserGeneratorExample();
            ex.createCriteria().andNameEqualTo(user.getName());
            List<UserGenerator> userGenerators = userGeneratorMapper.selectByExample(ex);
            String phone = userGenerators.get(0).getPhone();
            recordAnswer = new RecordAnswerGenerator();
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) p.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            String methodName = method.getName();
            if (methodName.equals("choiceAnswerJudge")){
                recordAnswer.setType("选择题");
            }else {
                recordAnswer.setType("简答题");
            }
            recordAnswer.setCourse("计组");
            recordAnswer.setProblemId(id);
            recordAnswer.setUserAnswer(answer);
            recordAnswer.setUserName(userName);
            recordAnswer.setUserPhone(phone);
            now = System.currentTimeMillis();
            Date date = DataConversion.getDate(now);
            recordAnswer.setDate(date);
        } catch (Exception e) {
            logger.error("答题切面出错了！！！",e);
        }

        //执行代理方法
        Object o = null;
        try {
            o = p.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException();
        }
        try {
            long next = System.currentTimeMillis();
            long timeConsume = next - now;
            recordAnswer.setTimeConsume(String.valueOf(timeConsume));
            recordAnswerGeneratorMapper.insertSelective(recordAnswer);
        } catch (Exception e) {
            logger.error("答题切面出错了！！！",e);
        }

        return o;
    }


}
