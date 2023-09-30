package com.example.SchoolMangementSystem.Entities;

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
public class CourseMaterialsMappingWithCourse {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String materialId;
    @ManyToOne
    @JoinColumn(name="courseId",referencedColumnName = "courseId")
    public CoursesModel coursesModel;
    public String fileUrl;
    public String fileId;

}
