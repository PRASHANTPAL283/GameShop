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

public class TeacherModel {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String teacherId;
    public String teacherName;
    public String email;
    public String city;
    @OneToOne(mappedBy = "teacherModel")
    @JsonManagedReference
    public TeacherAddress teacherAddress;

    @ManyToMany
    @JoinTable(
            name = "teacher_courses",
            joinColumns = @JoinColumn(
                    name = "teacherId", referencedColumnName = "teacherId"),
            inverseJoinColumns = @JoinColumn(
                    name = "coursesId", referencedColumnName = "courseId"))
    public List<CoursesModel> coursesModelList;

}
