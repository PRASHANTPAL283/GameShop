package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.Entities.StudentModel;
import com.example.SchoolMangementSystem.Services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {
    @Autowired
    public StudentServices studentServices;

    @PostMapping("/addStudent")
    public ResponseEntity<StudentModel> addNewStudent(@RequestBody StudentModel studentModel){
        return this.studentServices.addNewStudent(studentModel);
    }
    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable("id") String id){
        return this.studentServices.getStudentById(id);
    }
    @GetMapping("/allstudentByClass/{id}")
    public ResponseEntity<List<StudentModel>> getallstudentsByClassId(@PathVariable("id")String id){
        return this.studentServices.getallstudentsByClassId(id);
    }

    @GetMapping("/allstudents")
    public ResponseEntity<List<StudentModel>> getallstudents(){
        return this.studentServices.getallstudents();
    }
    @GetMapping("/deleteStudentById/{id}")
    public ResponseEntity<StudentModel> deleteStudentById(@PathVariable("id") String  id){
        return this.studentServices.deleteStudentById(id);
    }

    @GetMapping("/getStudentByName/{name}/{email}")
    public List<StudentModel> getstudentbyname(@PathVariable("name") String name , @PathVariable("email") String email){
        return this.studentServices.getStudentsByName(name,email);
    }

    @GetMapping("/deleteByQueryStudent/{id}")
    public void deleteStudentBYId(@PathVariable("id") String id){
        this.studentServices.deleteStudentByQueryId(id);
    }

    @GetMapping("/generateExcel")
    public String generateExcel() throws IOException {
        return this.studentServices.generateDataToExcel();
    }

}
