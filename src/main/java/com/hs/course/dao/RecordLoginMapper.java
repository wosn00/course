package com.hs.course.dao;

import com.hs.course.domaingenerator.RecordLoginGenerator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RecordLoginMapper {
    List<RecordLoginGenerator> selFenye(Map map);
}
