package com.hs.course.dao;

import com.hs.course.domaingenerator.QuestionsGenerator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface QuestionsMapper {
    List<QuestionsGenerator> selToFenye(Map map);
    Integer selCount();

}
