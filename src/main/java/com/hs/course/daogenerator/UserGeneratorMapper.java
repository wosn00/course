package com.hs.course.daogenerator;

import com.hs.course.domaingenerator.UserGenerator;
import com.hs.course.domaingenerator.UserGeneratorExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserGeneratorMapper {
    int countByExample(UserGeneratorExample example);

    int deleteByExample(UserGeneratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserGenerator record);

    int insertSelective(UserGenerator record);

    List<UserGenerator> selectByExample(UserGeneratorExample example);

    UserGenerator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserGenerator record, @Param("example") UserGeneratorExample example);

    int updateByExample(@Param("record") UserGenerator record, @Param("example") UserGeneratorExample example);

    int updateByPrimaryKeySelective(UserGenerator record);

    int updateByPrimaryKey(UserGenerator record);
}