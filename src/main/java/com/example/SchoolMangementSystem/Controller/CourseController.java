package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.Entities.CoursesModel;
import com.example.SchoolMangementSystem.Services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/course")
public class CourseController {
    @Autowired
    public CourseServices courseServices;
    @PostMapping("/addNewCourse")
    public ResponseEntity<CoursesModel> addNewCourse(@RequestBody CoursesModel coursesModel){
       return this.courseServices.addNewCourse(coursesModel);
    }
    @GetMapping("/allcourses")
    public ResponseEntity<List<CoursesModel>> getallcourses(){
        return this.courseServices.getallCourses();
    }
    @GetMapping("/allcourses/{id}")
    public ResponseEntity<CoursesModel> getcoursesById(@PathVariable("id") String id){
        return this.courseServices.getCourseById(id);
    }
    @GetMapping("/getCoursesByCategory/{name}")
    public ResponseEntity<List<CoursesModel>> getallCoursesByCategory(@PathVariable("name") String id){
        return this.courseServices.getallcoursesByCategory(id);
    }

    @GetMapping("/deleteCourseById/{id}")
    public ResponseEntity<CoursesModel> deleteCourseById(@PathVariable("id") String id){
        return this.courseServices.deleteCourseById(id);
    }
}
