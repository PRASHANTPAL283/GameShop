package com.example.SchoolMangementSystem.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherAddress {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String addId;
    @OneToOne
    @JsonBackReference
    @JoinColumn(name="teacherId", referencedColumnName = "teacherId")
    public TeacherModel teacherModel;

    public String city;
    public String addressdescription;
    public String landmark;
    public String state;
    public String pincode;
    public String ph;


}
