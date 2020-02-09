package com.hs.course.dao;

import com.hs.course.domaingenerator.RecordAnswerGenerator;
import com.hs.course.entity.Choice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RecordAnswerMapper {
    List<RecordAnswerGenerator> selFenye(Map map);
}
