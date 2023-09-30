package com.example.SchoolMangementSystem.DTOS;

import com.example.SchoolMangementSystem.Entities.CoursesModel;
import com.example.SchoolMangementSystem.Entities.StudentModel;
import com.example.SchoolMangementSystem.Entities.TeacherModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassDTO {
    public String classId;
    public String classGrade;
    public String section;
    public List<CoursesModel> coursesModelList;
}
