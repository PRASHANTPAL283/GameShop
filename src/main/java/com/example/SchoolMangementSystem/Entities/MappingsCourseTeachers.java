package com.example.SchoolMangementSystem.Entities;

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
public class MappingsCourseTeachers {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String mappindId;
    @OneToOne
    @JoinColumn(name="teacherId", referencedColumnName = "teacherId")
    public TeacherModel teacherModel;
    @OneToOne
    @JoinColumn(name="courseId",referencedColumnName = "courseId")
    public CoursesModel coursesModel;

    @ManyToOne
    @JoinColumn(name="classId", referencedColumnName = "classId")
    public ClassModel classModel;

}
