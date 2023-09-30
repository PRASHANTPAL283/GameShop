package com.example.SchoolMangementSystem.Entities;

import com.example.SchoolMangementSystem.DTOS.ClassDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class StudentModel {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String studentId;
    public String studentName;
    public String studentEmail;
    public String city;
    @OneToOne(mappedBy ="studentModel")
    public StudentAddress studentAddress;

    @ManyToMany
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(
                    name = "studentId", referencedColumnName = "studentId"),
            inverseJoinColumns = @JoinColumn(
                    name = "coursesId", referencedColumnName = "courseId"))
    public List<CoursesModel> coursesModelList;
    @ManyToOne
    @JoinColumn(name="classId",referencedColumnName = "classId")
    @JsonBackReference
    public ClassModel classModel;

    @Transient
    public ClassDTO classDTO;

}
