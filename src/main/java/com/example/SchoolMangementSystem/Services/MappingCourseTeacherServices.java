package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.Dao.MappingCourseWithTeacherDao;
import com.example.SchoolMangementSystem.Entities.MappingsCourseTeachers;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MappingCourseTeacherServices {
    @Autowired
    public MappingCourseWithTeacherDao teacherDao;
    public ResponseEntity<MappingsCourseTeachers> addNewMapping(MappingsCourseTeachers teachers){
        try{
            MappingsCourseTeachers c=this.teacherDao.save(teachers);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(c);
        }
        catch (Exception e){
            throw new GlobalExceptionClass(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MappingsCourseTeachers> getMappingById(String id){
        Optional<MappingsCourseTeachers> t=this.teacherDao.findById(id);
        if(t.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(t.get());
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<MappingsCourseTeachers>> getalldataByClassId(String id){
        List<MappingsCourseTeachers> list=new ArrayList<>();
        this.teacherDao.findAll().stream()
                .forEach(e->{
                    if(e.getClassModel().getClassId().equals(id)){
                        list.add(e);
                    }
                });
        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }

    public ResponseEntity<MappingsCourseTeachers> deleteMappingById(String id){
        Optional<MappingsCourseTeachers> mapping=this.teacherDao.findById(id);
        if(mapping.isPresent()){
            this.teacherDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapping.get());
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }


}
