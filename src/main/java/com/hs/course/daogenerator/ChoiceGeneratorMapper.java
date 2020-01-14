package com.hs.course.daogenerator;

import com.hs.course.domaingenerator.ChoiceGenerator;
import com.hs.course.domaingenerator.ChoiceGeneratorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChoiceGeneratorMapper {
    int countByExample(ChoiceGeneratorExample example);

    int deleteByExample(ChoiceGeneratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChoiceGenerator record);

    int insertSelective(ChoiceGenerator record);

    List<ChoiceGenerator> selectByExample(ChoiceGeneratorExample example);

    ChoiceGenerator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChoiceGenerator record, @Param("example") ChoiceGeneratorExample example);

    int updateByExample(@Param("record") ChoiceGenerator record, @Param("example") ChoiceGeneratorExample example);

    int updateByPrimaryKeySelective(ChoiceGenerator record);

    int updateByPrimaryKey(ChoiceGenerator record);
}