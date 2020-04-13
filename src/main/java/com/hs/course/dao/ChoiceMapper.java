package com.hs.course.dao;

import com.hs.course.entity.Choice;
import com.hs.course.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ChoiceMapper {
    List<Choice> selToFenye(Map map);
    int selCount(Map map);
    Choice selCounntno(Map map);
    List<Choice> selByRand(Map map);
    List<Choice> selFuzzyFenye(Map map);
    int selFuzzyCount(@Param(value="searchText") String searchText);
}
