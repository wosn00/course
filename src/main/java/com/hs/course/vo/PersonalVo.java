package com.hs.course.vo;

import com.hs.course.domaingenerator.UserGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalVo {
    String name;
    String className;
    String studentID;
    String phone;
    String date;

    public static PersonalVo of(UserGenerator v){
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        String date = df.format(v.getDate());
        return PersonalVo.builder()
                .className(v.getClassname())
                .date(date)
                .name(v.getName())
                .phone(v.getPhone())
                .studentID(v.getStudentid())
                .build();
    }


}
