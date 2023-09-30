package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.MappingsCourseTeachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MappingCourseWithTeacherDao extends JpaRepository<MappingsCourseTeachers,String> {



}
