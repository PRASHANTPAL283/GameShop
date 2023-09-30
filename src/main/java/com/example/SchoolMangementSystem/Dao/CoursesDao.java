package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.CoursesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CoursesDao extends JpaRepository<CoursesModel,String> {
    public CoursesModel findByCourseName(String name);

    @Modifying
    @Transactional
    @Query(value="delete from courses_classes where course_id=?1",nativeQuery = true)
    public int deleteCoursesFromClasses(String courseId);


    @Modifying
    @Transactional
    @Query(value="delete from teacher_course_data_for_class where course_id=?1",nativeQuery = true)
    public int deleteCourseFromOverallDataClasses(String courseId);
}
