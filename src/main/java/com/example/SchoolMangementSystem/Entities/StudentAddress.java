package com.example.SchoolMangementSystem.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class StudentAddress {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    public String addId;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name="studentId", referencedColumnName = "studentId")
    public StudentModel studentModel;

    public String city;
    public String addressdescription;
    public String landmark;
    public String state;
    public String pincode;
    public String ph;

}
