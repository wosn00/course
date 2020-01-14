package com.hs.course.daogenerator;

import com.hs.course.domaingenerator.QuestionsGenerator;
import com.hs.course.domaingenerator.QuestionsGeneratorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionsGeneratorMapper {
    int countByExample(QuestionsGeneratorExample example);

    int deleteByExample(QuestionsGeneratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuestionsGenerator record);

    int insertSelective(QuestionsGenerator record);

    List<QuestionsGenerator> selectByExample(QuestionsGeneratorExample example);

    QuestionsGenerator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuestionsGenerator record, @Param("example") QuestionsGeneratorExample example);

    int updateByExample(@Param("record") QuestionsGenerator record, @Param("example") QuestionsGeneratorExample example);

    int updateByPrimaryKeySelective(QuestionsGenerator record);

    int updateByPrimaryKey(QuestionsGenerator record);
}