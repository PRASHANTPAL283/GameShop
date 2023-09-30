package com.example.SchoolMangementSystem.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class AttendanceModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String attendenceId;
    @OneToOne
    @JoinColumn(name="studentId",referencedColumnName = "studentId")
    public StudentModel studentModel;

    @OneToOne
    @JoinColumn(name="teacherId",referencedColumnName = "teacherId")
    public TeacherModel teacherModel;

    public Date date;

    @OneToOne
    @JoinColumn(name="timeTableId",referencedColumnName = "timeTableId")
    public Timetable timetable;



}
