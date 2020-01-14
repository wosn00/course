package com.hs.course.daogenerator;

import com.hs.course.domaingenerator.SummaryGenerator;
import com.hs.course.domaingenerator.SummaryGeneratorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SummaryGeneratorMapper {
    int countByExample(SummaryGeneratorExample example);

    int deleteByExample(SummaryGeneratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SummaryGenerator record);

    int insertSelective(SummaryGenerator record);

    List<SummaryGenerator> selectByExample(SummaryGeneratorExample example);

    SummaryGenerator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SummaryGenerator record, @Param("example") SummaryGeneratorExample example);

    int updateByExample(@Param("record") SummaryGenerator record, @Param("example") SummaryGeneratorExample example);

    int updateByPrimaryKeySelective(SummaryGenerator record);

    int updateByPrimaryKey(SummaryGenerator record);
}