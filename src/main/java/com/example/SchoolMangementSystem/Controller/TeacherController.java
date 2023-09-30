package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.DTOS.TeacherCourseRequest;
import com.example.SchoolMangementSystem.Entities.TeacherModel;
import com.example.SchoolMangementSystem.Services.TeacherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    public TeacherServices services;
    @PostMapping("/addNewTeacher")
    public ResponseEntity<TeacherModel> addNewTeacher(@RequestBody TeacherModel teacherModel){
        return this.services.addNewTeacher(teacherModel);
    }
    @GetMapping("/allteachers")
    public ResponseEntity<List<TeacherModel>> getallteachers(){
        return this.services.getallteachers();
    }
    @GetMapping("/allteachersbyCourseId/{id}")
    public ResponseEntity<List<TeacherModel>> getTeacherByCourseId(@PathVariable("id") String id){
        return this.services.getallteachersByCourseId(id);
    }

    @PostMapping("/addNewCourseByTeacherId")
    public ResponseEntity<TeacherModel> addNewCourseByTeacherId(@RequestBody TeacherCourseRequest cr){
        return this.services.addNewCourseByTeacherId(cr);
    }
    @PostMapping("/removeCourseByTeacherId")
    public ResponseEntity<TeacherModel> removeCourseByTeacherId(@RequestBody TeacherCourseRequest cr){
        return this.services.removeCourseByTeacherId(cr);
    }

    @GetMapping("/deleteTeacherById/{id}")
    public ResponseEntity<TeacherModel> deleteTeacherById(@PathVariable("id") String id){
        return this.services.deleteTeacherbyId(id);
    }
}
