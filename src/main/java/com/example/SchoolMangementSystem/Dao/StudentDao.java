package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentDao extends JpaRepository<StudentModel,String> {
    public StudentModel findByStudentEmail(String mail);

    @Query("select u from StudentModel u where u.studentName=:name and u.studentEmail=:email")
    public List<StudentModel> getstudentbycity(@Param("name") String name, @Param("email") String email);



    @Modifying
    @Query("delete from StudentModel u where u.studentId=:id")
    public void deleteStudentByItsId(@Param("id") String id);


    @Modifying
    @Transactional
    @Query(value="delete from students_courses where student_id=?1",nativeQuery = true)
    public int deletefromstudentCourses(String stuId);





}
