package com.example.SchoolMangementSystem.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Timetable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String timeTableId;
    @OneToOne
    @JoinColumn(name="courseId", referencedColumnName = "courseId")
    public CoursesModel coursesModel;

    public String startTime;

    public String endTime;

    public String day;
    public Date date;

    @ManyToOne
    @JoinColumn(name="classId",referencedColumnName = "classId")
    public ClassModel classModel;

    @OneToOne
    @JoinColumn(name="teacherId", referencedColumnName = "teacherId")
    public TeacherModel teacherModel;


}
