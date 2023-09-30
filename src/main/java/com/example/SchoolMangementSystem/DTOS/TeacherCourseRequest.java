package com.example.SchoolMangementSystem.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherCourseRequest {
    public String courseId;
    public String teacherId;
}
