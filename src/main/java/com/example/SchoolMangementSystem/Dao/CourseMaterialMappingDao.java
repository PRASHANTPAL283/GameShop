package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.CourseMaterialsMappingWithCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMaterialMappingDao extends JpaRepository<CourseMaterialsMappingWithCourse,String> {
}
