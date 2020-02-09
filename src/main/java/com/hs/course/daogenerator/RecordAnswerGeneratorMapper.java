package com.hs.course.daogenerator;

import com.hs.course.domaingenerator.RecordAnswerGenerator;
import com.hs.course.domaingenerator.RecordAnswerGeneratorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RecordAnswerGeneratorMapper {
    int countByExample(RecordAnswerGeneratorExample example);

    int deleteByExample(RecordAnswerGeneratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecordAnswerGenerator record);

    int insertSelective(RecordAnswerGenerator record);

    List<RecordAnswerGenerator> selectByExample(RecordAnswerGeneratorExample example);

    RecordAnswerGenerator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecordAnswerGenerator record, @Param("example") RecordAnswerGeneratorExample example);

    int updateByExample(@Param("record") RecordAnswerGenerator record, @Param("example") RecordAnswerGeneratorExample example);

    int updateByPrimaryKeySelective(RecordAnswerGenerator record);

    int updateByPrimaryKey(RecordAnswerGenerator record);
}