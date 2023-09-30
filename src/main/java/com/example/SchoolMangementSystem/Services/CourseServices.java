package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.Dao.CoursesDao;
import com.example.SchoolMangementSystem.Dao.TeacherDao;
import com.example.SchoolMangementSystem.Entities.CoursesModel;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseServices {
    @Autowired
    public CoursesDao coursesDao;
    @Autowired
    public TeacherDao teacherDao;

    public ResponseEntity<CoursesModel> addNewCourse(CoursesModel coursesModel){
        try{
            Date date=new java.util.Date();
            coursesModel.setDate(date);
            Optional<CoursesModel> optionalCoursesModel= Optional.of(this.coursesDao.save(coursesModel));
            if(optionalCoursesModel.isPresent()){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(optionalCoursesModel.get());
            }
            else{
                throw new GlobalExceptionClass("ERROR_IN_ADDING_DATA",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception exception){
            throw new GlobalExceptionClass(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<CoursesModel>> getallCourses(){
        List<CoursesModel> coursesModelList=new ArrayList<>();
        coursesModelList=this.coursesDao.findAll();
        Collections.sort(coursesModelList, new Comparator<CoursesModel>() {
            @Override
            public int compare(CoursesModel o1, CoursesModel o2) {
                return o1.getCourseId().compareTo(o2.getCourseId());
            }
        });
       return ResponseEntity.status(HttpStatus.OK)
               .body(coursesModelList);
    }
    public ResponseEntity<CoursesModel> getCourseById(String id){
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(id);
        if(coursesModel.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(coursesModel.get());
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<CoursesModel> deleteCourseById(String id){
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(id);
        if(coursesModel.isPresent()){
            CoursesModel c=new CoursesModel();
            c.setCourseId(coursesModel.get().courseId);
            c.setCourseAuthor(coursesModel.get().getCourseAuthor());
            c.setCourseName(coursesModel.get().getCourseName());
            c.setDate(coursesModel.get().getDate());
            this.teacherDao.delteCourseFromTeachers(coursesModel.get().getCourseId());
            this.coursesDao.deleteCoursesFromClasses(coursesModel.get().getCourseId());
            this.coursesDao.deleteCourseFromOverallDataClasses(coursesModel.get().getCourseId());
            this.teacherDao.deleteCourseFromStudentCourses(coursesModel.get().getCourseId());
            this.coursesDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(c);
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_THERE",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<CoursesModel>> getallcoursesByCategory(String id){
        List<CoursesModel> coursesModelList=new ArrayList<>();
        this.coursesDao.findAll().stream().forEach(e->{
            if(e.getCategory().equals(id)){
                coursesModelList.add(e);
            }
        });
        return ResponseEntity.status(HttpStatus.OK)
                .body(coursesModelList);
    }




}
