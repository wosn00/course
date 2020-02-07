package com.hs.course.daogenerator;

import com.hs.course.domaingenerator.TotalAnswerGenerator;
import com.hs.course.domaingenerator.TotalAnswerGeneratorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TotalAnswerGeneratorMapper {
    int countByExample(TotalAnswerGeneratorExample example);

    int deleteByExample(TotalAnswerGeneratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TotalAnswerGenerator record);

    int insertSelective(TotalAnswerGenerator record);

    List<TotalAnswerGenerator> selectByExample(TotalAnswerGeneratorExample example);

    TotalAnswerGenerator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TotalAnswerGenerator record, @Param("example") TotalAnswerGeneratorExample example);

    int updateByExample(@Param("record") TotalAnswerGenerator record, @Param("example") TotalAnswerGeneratorExample example);

    int updateByPrimaryKeySelective(TotalAnswerGenerator record);

    int updateByPrimaryKey(TotalAnswerGenerator record);
}