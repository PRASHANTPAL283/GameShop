package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.DTOS.ClassStudentChangeRequest;
import com.example.SchoolMangementSystem.DTOS.TeacherClassAssignRequest;
import com.example.SchoolMangementSystem.Entities.ClassModel;
import com.example.SchoolMangementSystem.Services.ClassServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/class")
public class ClassController {

    @Autowired
    public ClassServices classServices;
    @PostMapping("/addClass")
    public ResponseEntity<ClassModel> addnewClass(@RequestBody ClassModel classModel){
        return this.classServices.addNewClass(classModel);
    }
    @PostMapping("/addStudentToClass")
    public ResponseEntity<ClassModel> addStudentToClass(@RequestBody ClassStudentChangeRequest cr){
        return this.classServices.addNewStudent(cr);
    }
    @PostMapping("/removeStudentfromClass")
    public ResponseEntity<ClassModel> removeStudentFromClass(@RequestBody ClassStudentChangeRequest cr){
        return this.classServices.removeStudentFromClass(cr);
    }
    @GetMapping("/allclasses")
    public ResponseEntity<List<ClassModel>> getallclasses(){
        return this.classServices.getallclasses();
    }

    @GetMapping("/allclasses/{id}")
    public ResponseEntity<ClassModel> getClassById(@PathVariable("id") String id){
        return this.classServices.getClassById(id);
    }

    @GetMapping("/deleteClassById/{id}")
    public ResponseEntity<ClassModel> deleteClassById(@PathVariable("id") String id){
        return this.classServices.deleteClassById(id);
    }

    @PostMapping("/addCourseByClassId/{id}")
    public ResponseEntity<ClassModel> addCourseByClass(@RequestBody ClassModel classModel, @PathVariable("id") String id){
        return this.classServices.addNewCourse(classModel,id);
    }
    @PostMapping("/removeCourseByClassId/{id}")
    public ResponseEntity<ClassModel> removeCourseById(@RequestBody ClassModel classModel, @PathVariable("id") String id){
        return this.classServices.removeCourseById(classModel, id);
    }

    @PostMapping("/addNewTeacherByClass")
    public ResponseEntity<ClassModel> addNewTeacher(@RequestBody TeacherClassAssignRequest request){
        return this.classServices.addNewTeacherForClass(request);
    }
    @PostMapping("/removeTeacherByClass")
    public ResponseEntity<ClassModel> removeTeacher(@RequestBody TeacherClassAssignRequest request){
        return this.classServices.removeTeacherFromClass(request);
    }
}
