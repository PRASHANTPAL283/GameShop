package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.Dao.CourseMaterialMappingDao;
import com.example.SchoolMangementSystem.Dao.CoursesDao;
import com.example.SchoolMangementSystem.Entities.CourseMaterialsMappingWithCourse;
import com.example.SchoolMangementSystem.Entities.CoursesModel;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseMaterialServices {

    @Autowired
    public CourseMaterialMappingDao courseMaterialMappingDao;

    @Autowired
    public CoursesDao coursesDao;

    public ResponseEntity<CourseMaterialsMappingWithCourse> addNewCourseMapping(CourseMaterialsMappingWithCourse cr){
        try{
            CourseMaterialsMappingWithCourse courseMaterialsMappingWithCourse
                    =this.courseMaterialMappingDao.save(cr);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(courseMaterialsMappingWithCourse);
        }
        catch (Exception e){
            throw new GlobalExceptionClass("ERROR_IN_ADDING_MAPPING",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<CourseMaterialsMappingWithCourse>> getallcoursesMaterialsbycourseId(String id){
       List<CourseMaterialsMappingWithCourse> courseMaterialsMappingWithCourses=new ArrayList<>();
       this.courseMaterialMappingDao.findAll().stream()
               .forEach(e->{
                   if(e.getCoursesModel().getCourseId().equals(id)){
                       courseMaterialsMappingWithCourses.add(e);
                   }
               });
       return ResponseEntity.status(HttpStatus.OK)
               .body(courseMaterialsMappingWithCourses);
    }

    public int flag=0;
    public int checkIfMaterialAlreadyPresent(String fileId,List<CourseMaterialsMappingWithCourse> courses){
        flag=0;
        courses.stream().forEach(e->{
            if(e.getFileId().equals(fileId)){
                flag=1;
            }
        });
        return flag;

    }

    public ResponseEntity<CoursesModel> addNewMaterialByCourseId(CourseMaterialsMappingWithCourse cr, String courseId){
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(courseId);
        if(coursesModel.isPresent()==false){
            throw new GlobalExceptionClass("COURSE_MODEL_NOT_PRESENT",HttpStatus.NOT_FOUND);
        }
        else if(this.checkIfMaterialAlreadyPresent(cr.getFileId(),coursesModel.get().getCourseMaterialsMappingWithCourses())==1){
            throw new GlobalExceptionClass("COURSE_MATERIAL_ALREADY_PRESENT",HttpStatus.BAD_REQUEST);
        }
        else{
            List<CourseMaterialsMappingWithCourse> courses=new ArrayList<>();
            courses=coursesModel.get().getCourseMaterialsMappingWithCourses();
            courses.add(cr);
            coursesModel.get().setCourseMaterialsMappingWithCourses(courses);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.coursesDao.save(coursesModel.get()));
        }

    }

    public ResponseEntity<CoursesModel> removeCourseById(CourseMaterialsMappingWithCourse cr, String courseId){
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(courseId);
        if(coursesModel.isPresent()==false){
            throw new GlobalExceptionClass("COURSE_MODEL_NOT_PRESENT",HttpStatus.NOT_FOUND);
        }
        else if(this.checkIfMaterialAlreadyPresent(cr.getFileId(),coursesModel.get().getCourseMaterialsMappingWithCourses())==0){
            throw new GlobalExceptionClass("COURSE_MATERIAL_NOT_PRESENT",HttpStatus.BAD_REQUEST);
        }
        else{
            List<CourseMaterialsMappingWithCourse> courses=new ArrayList<>();
            courses=coursesModel.get().getCourseMaterialsMappingWithCourses();
            courses.remove(cr);
            coursesModel.get().setCourseMaterialsMappingWithCourses(courses);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.coursesDao.save(coursesModel.get()));
        }

    }
}
