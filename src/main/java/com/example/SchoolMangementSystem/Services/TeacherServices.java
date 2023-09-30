package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.DTOS.TeacherCourseRequest;
import com.example.SchoolMangementSystem.Dao.CoursesDao;
import com.example.SchoolMangementSystem.Dao.TeacherAddressDao;
import com.example.SchoolMangementSystem.Dao.TeacherDao;
import com.example.SchoolMangementSystem.Entities.CoursesModel;
import com.example.SchoolMangementSystem.Entities.TeacherAddress;
import com.example.SchoolMangementSystem.Entities.TeacherCourseDataForClass;
import com.example.SchoolMangementSystem.Entities.TeacherModel;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TeacherServices {

    @Autowired
    public TeacherDao teacherDao;

    public ResponseEntity<TeacherModel> addNewTeacher(TeacherModel teacherModel){
        try{
            TeacherModel teacherModel1=this.teacherDao.save(teacherModel);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(teacherModel1);
        }
        catch (Exception e){
            throw new GlobalExceptionClass(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<TeacherModel>> getallteachers(){
        List<TeacherModel> teacherModels=new ArrayList<>();
        teacherModels=this.teacherDao.findAll();
        Collections.sort(teacherModels, new Comparator<TeacherModel>() {
            @Override
            public int compare(TeacherModel o1, TeacherModel o2) {
                return o1.getTeacherId().compareTo(o2.getTeacherId());
            }
        });
       return ResponseEntity.status(HttpStatus.OK)
               .body(teacherModels);
    }
    public int flag=0;

    public int checkIfteacherHaveCourseOrNot(List<CoursesModel> coursesModelList, String courseId){
        flag=0;
        coursesModelList.stream().forEach(e->{
            if(e.getCourseId().equals(courseId)){
                flag=1;
            }
        });
        return flag;

    }

    public ResponseEntity<List<TeacherModel>> getallteachersByCourseId(String courseId){
        List<TeacherModel> teacherModels=new ArrayList<>();
        this.teacherDao.findAll().stream()
                .forEach(e->{
                    if(this.checkIfteacherHaveCourseOrNot(e.getCoursesModelList(),courseId)==1){
                        teacherModels.add(e);
                    }
                });
        return ResponseEntity.status(HttpStatus.OK)
                .body(teacherModels);
    }
    @Autowired
    public CoursesDao coursesDao;


    public ResponseEntity<TeacherModel> addNewCourseByTeacherId(TeacherCourseRequest cr){
        Optional<TeacherModel> teacherModel=this.teacherDao.findById(cr.getTeacherId());
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(cr.getCourseId());

        if(teacherModel.isPresent()==false){
            throw new GlobalExceptionClass("TEACHER_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        else if(coursesModel.isPresent()==false){
            throw new GlobalExceptionClass("COURSE_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        else if(this.checkIfteacherHaveCourseOrNot(teacherModel.get().getCoursesModelList(),cr.getCourseId())==1){
            throw new GlobalExceptionClass("COURSE_ALREADY_PRESENT",HttpStatus.BAD_REQUEST);
        }
        else{
           int p1= this.teacherDao.addNewCoursesByOuery(teacherModel.get().getTeacherId(),coursesModel.get().getCourseId());
           if(p1>=1){
               return ResponseEntity.status(HttpStatus.OK).body(this.teacherDao.getTeacherByQuery(teacherModel.get().getTeacherId()));
           }
           else{
               throw new GlobalExceptionClass("DATA INSERTION FAILED",HttpStatus.INTERNAL_SERVER_ERROR);
           }



        }


    }

    @Transactional
    public ResponseEntity<TeacherModel> removeCourseByTeacherId(TeacherCourseRequest cr){
        Optional<TeacherModel> teacherModel=this.teacherDao.findById(cr.getTeacherId());
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(cr.getCourseId());

        if(teacherModel.isPresent()==false){
            throw new GlobalExceptionClass("TEACHER_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        else if(coursesModel.isPresent()==false){
            throw new GlobalExceptionClass("COURSE_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        else if(this.checkIfteacherHaveCourseOrNot(teacherModel.get().getCoursesModelList(),cr.getCourseId())==0){
            throw new GlobalExceptionClass("COURSE_NOT_PRESENT",HttpStatus.BAD_REQUEST);
        }
        else{
            int p1=this.teacherDao.removeCourseByQuery(teacherModel.get().getTeacherId(),coursesModel.get().getCourseId());
            if(p1>=1){
                return ResponseEntity.status(HttpStatus.OK).body(this.teacherDao.getTeacherByQuery(teacherModel.get().getTeacherId()));
            }
            else{
                throw new GlobalExceptionClass("DATA CHANGES FAILED",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
    @Autowired
    public TeacherAddressDao addressDao;

    @Transactional
    public ResponseEntity<TeacherModel> deleteTeacherbyId(String id){
        Optional<TeacherModel> teacherModel=this.teacherDao.findById(id);
        List<TeacherAddress> teacherAddresses=new ArrayList<>();
        teacherModel.get().setCoursesModelList(null);
        this.teacherDao.save(teacherModel.get());


        teacherAddresses=this.addressDao.findAll();
        teacherAddresses.stream().forEach(e->{
            if(e.getTeacherModel().getTeacherId().equals(id)){
                this.addressDao.deleteById(e.getAddId());
            }
        });

        if(teacherModel.isPresent()){
            TeacherModel teacherModel1=new TeacherModel();
            teacherModel1.setTeacherId(teacherModel.get().getTeacherId());
            this.teacherDao.deleteFromTeacherCoursesData(teacherModel.get().getTeacherId());
            this.teacherDao.deleteFromTeacherClasses(teacherModel.get().getTeacherId());
            this.teacherDao.deleteFromTeacherCoursesDataForClasses(teacherModel.get().getTeacherId());
            this.teacherDao.deleteTeacherAddressData(teacherModel.get().getTeacherId());
            this.teacherDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(teacherModel1);
        }
        else{
            throw new GlobalExceptionClass("TEACHER_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }






}
