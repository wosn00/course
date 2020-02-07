package com.hs.course.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface TotalAnswerMapper {
    Float selAvg(Map map);

}
