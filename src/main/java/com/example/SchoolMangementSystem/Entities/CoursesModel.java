package com.example.SchoolMangementSystem.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoursesModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String courseId;
    public String courseName;
    public String courseAuthor;
    public String category;
    @CreationTimestamp
    public Date date;

    @OneToMany(mappedBy = "coursesModel",cascade = CascadeType.MERGE)
   public List<CourseMaterialsMappingWithCourse> courseMaterialsMappingWithCourses;
}
