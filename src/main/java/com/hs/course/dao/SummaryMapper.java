package com.hs.course.dao;

import com.hs.course.entity.Summary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SummaryMapper {
    List<Summary> selToFenye(Map map);
    int selCount(Map map);
    Summary selCounntno(Map map);
    List<Summary> selByRand(Map map);
    List<Summary> selFuzzyFenye(Map map);
    int selFuzzyCount(@Param(value="searchText") String searchText);
}
