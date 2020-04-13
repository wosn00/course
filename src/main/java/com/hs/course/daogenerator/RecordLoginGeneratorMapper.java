package com.hs.course.daogenerator;

import com.hs.course.domaingenerator.RecordLoginGenerator;
import com.hs.course.domaingenerator.RecordLoginGeneratorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RecordLoginGeneratorMapper {
    int countByExample(RecordLoginGeneratorExample example);

    int deleteByExample(RecordLoginGeneratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecordLoginGenerator record);

    int insertSelective(RecordLoginGenerator record);

    List<RecordLoginGenerator> selectByExample(RecordLoginGeneratorExample example);

    RecordLoginGenerator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecordLoginGenerator record, @Param("example") RecordLoginGeneratorExample example);

    int updateByExample(@Param("record") RecordLoginGenerator record, @Param("example") RecordLoginGeneratorExample example);

    int updateByPrimaryKeySelective(RecordLoginGenerator record);

    int updateByPrimaryKey(RecordLoginGenerator record);
}