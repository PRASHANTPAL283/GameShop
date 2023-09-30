package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClassDao extends JpaRepository<ClassModel,String> {


    @Modifying
    @Transactional
    @Query(value = "insert into courses_classes (class_id, course_id) values (?1, ?2)",nativeQuery = true)
    public int addNewCourseByQuery(String classId, String courseId);

    @Modifying
    @Transactional
    @Query(value="delete from courses_classes where class_id=?1 and course_id=?2",nativeQuery = true)
    public int removeCourseByQuery(String classId, String courseId);

    @Query("select u from ClassModel u where u.classId=:id")
    public ClassModel getClassByItsId(@Param("id") String id);


    @Modifying
    @Transactional
    @Query(value="delete from courses_classes where class_id=?1",nativeQuery = true)
    public int deleteClassesDataFromCoursesClass(String classId);


    @Modifying
    @Transactional
    @Query(value="delete from teacher_classes where class_id=?1",nativeQuery = true)
    public int deleteClassesDatafromTeacherClasses(String classId);



}
