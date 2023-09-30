package com.example.SchoolMangementSystem.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherClassAssignRequest {
    public String classId;
    public String teacherId;
    public String courseId;

}
