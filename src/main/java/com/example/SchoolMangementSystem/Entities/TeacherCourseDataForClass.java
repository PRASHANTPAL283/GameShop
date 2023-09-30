package com.example.SchoolMangementSystem.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class TeacherCourseDataForClass {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    public String dataId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="classId",referencedColumnName = "classId")
    public ClassModel classModel;

    @OneToOne
    @JoinColumn(name="courseId",referencedColumnName = "courseId")
    public CoursesModel coursesModel;

    @OneToOne
    @JoinColumn(name="teacherId",referencedColumnName = "teacherId")
    public TeacherModel teacherModel;
}
