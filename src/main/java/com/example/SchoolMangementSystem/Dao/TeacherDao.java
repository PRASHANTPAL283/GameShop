package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TeacherDao extends JpaRepository<TeacherModel,String> {
    public TeacherModel findByEmail(String mail);

    @Modifying
    @Transactional
    @Query(value = "insert into teacher_courses (teacher_id, courses_id) values (?1, ?2)",nativeQuery = true)
    public int addNewCoursesByOuery( String id, String courseId);

    @Query("select u from TeacherModel u where u.teacherId=:teacherId")
    public TeacherModel getTeacherByQuery(@Param("teacherId") String teacherId);


    @Modifying
    @Transactional
    @Query(value="delete from teacher_courses where teacher_id=?1 and courses_id=?2",nativeQuery = true)
    public int removeCourseByQuery(String id,String courseId);


    @Modifying
    @Transactional
    @Query(value="insert into teacher_classes (class_id, teacher_id) values (?1, ?2)",nativeQuery = true)
    public int addTeacherQueryforClass(String classid, String teacherId);

    @Modifying
    @Transactional
    @Query(value = "delete from teacher_classes where class_id=?1 and teacher_id=?2",nativeQuery = true)
    public int removeTeacherQueryForClass(String classid, String teacherid);


    @Modifying
    @Transactional
    @Query(value = "delete from teacher_courses where courses_id=?1",nativeQuery = true)
    public int delteCourseFromTeachers(String courseId);


    @Modifying
    @Transactional
    @Query(value = "delete from students_courses where courses_id=?1",nativeQuery = true)
    public int deleteCourseFromStudentCourses(String courseId);


    @Modifying
    @Transactional
    @Query(value="delete from teacher_address where teacher_id=?1",nativeQuery = true)
    public int deleteTeacherAddressData(String teacherId);



    @Transactional
    @Modifying
    @Query(value="delete from teacher_classes where teacher_id=?1",nativeQuery = true)
    public int deleteFromTeacherClasses(String teacherId);


    @Transactional
    @Modifying
    @Query(value="delete from teacher_course_data_for_class where teacher_id=?1",nativeQuery = true)
    public int deleteFromTeacherCoursesDataForClasses(String teacherId);


    @Modifying
    @Transactional
    @Query(value="delete from teacher_courses where teacher_id=?1",nativeQuery = true)
    public int deleteFromTeacherCoursesData(String teacherId);





}
