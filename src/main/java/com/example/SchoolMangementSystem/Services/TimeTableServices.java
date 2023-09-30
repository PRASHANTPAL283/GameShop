package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.Dao.ClassDao;
import com.example.SchoolMangementSystem.Dao.TeacherDao;
import com.example.SchoolMangementSystem.Dao.TimeTableDao;
import com.example.SchoolMangementSystem.Entities.ClassModel;
import com.example.SchoolMangementSystem.Entities.TeacherModel;
import com.example.SchoolMangementSystem.Entities.Timetable;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeTableServices {
    @Autowired
    public TimeTableDao timeTableDao;

    @Autowired
    public TeacherDao teacherDao;

    @Autowired
    public ClassDao classDao;
    Optional<TeacherModel> teacherModel;
    public ResponseEntity<Timetable> addNewTimeTable(Timetable timetable){
        try{
            Optional<ClassModel> classModel=this.classDao.findById(timetable.getClassModel().getClassId());
            Timetable data=this.timeTableDao.save(timetable);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(data);
        }
        catch (Exception e){
            throw new GlobalExceptionClass(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Timetable>> getallTimeTables(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.timeTableDao.findAll());
    }

    public ResponseEntity<List<Timetable>> getTimeTableByClassId(String id){
        List<Timetable> timetableList=new ArrayList<>();
        this.timeTableDao.findAll().stream()
                .forEach(e->{
                    if(e.getClassModel().getClassId().equals(id)){
                        timetableList.add(e);
                    }
                });
        return ResponseEntity.status(HttpStatus.OK)
                .body(timetableList);
    }
   public ResponseEntity<Timetable> deleteTimeTableById(String id){
       Optional<Timetable> timetable=this.timeTableDao.findById(id);
       if(timetable.isPresent()){
           this.timeTableDao.deleteById(id);
           return ResponseEntity.status(HttpStatus.OK)
                   .body(timetable.get());
       }
       else{
           throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
       }
   }
   public ResponseEntity<Timetable> getTimeTableById(String id){
        Optional<Timetable> timetable=this.timeTableDao.findById(id);
        if(timetable.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(timetable.get());
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
   }

}
