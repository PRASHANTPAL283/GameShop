package com.example.SchoolMangementSystem.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String classId;
    public String classGrade;
    @OneToMany(mappedBy = "classModel")
    @JsonManagedReference
   public List<StudentModel> studentModels;

    @ManyToMany
    @JoinTable(
            name = "teacher_classes",
            joinColumns = @JoinColumn(
                    name = "classId", referencedColumnName = "classId"),
            inverseJoinColumns = @JoinColumn(
                    name = "teacherId", referencedColumnName = "teacherId"))
   public List<TeacherModel> teacherModelList;

    @OneToMany(mappedBy = "classModel")
    @JsonManagedReference
    public List<TeacherCourseDataForClass> teacherCourseDataForClasses;

   public String section;

   @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
   @JoinTable(
           name = "courses_classes",
           joinColumns = @JoinColumn(
                   name = "classId", referencedColumnName = "classId"),
           inverseJoinColumns = @JoinColumn(
                   name = "courseId", referencedColumnName = "courseId"))
   public List<CoursesModel> coursesModelList;

}
