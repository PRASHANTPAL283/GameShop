package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.TeacherAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherAddressDao extends JpaRepository<TeacherAddress,String> {
}
