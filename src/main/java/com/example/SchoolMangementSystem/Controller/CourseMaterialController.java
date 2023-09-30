package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.Entities.CourseMaterialsMappingWithCourse;
import com.example.SchoolMangementSystem.Entities.CoursesModel;
import com.example.SchoolMangementSystem.Services.CourseMaterialServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/courseMaterial")
public class CourseMaterialController {
    @Autowired
    public CourseMaterialServices services;

    @PostMapping("/addMaterial")
    public ResponseEntity<CourseMaterialsMappingWithCourse> addNewCourse(@RequestBody CourseMaterialsMappingWithCourse cr){
        return this.services.addNewCourseMapping(cr);
    }
    @GetMapping("/getCoursesMaterialByCourseId/{id}")
    public ResponseEntity<List<CourseMaterialsMappingWithCourse>> getallcourses(@PathVariable("id") String id){
        return this.services.getallcoursesMaterialsbycourseId(id);
    }

    @PostMapping("/addMaterialByCourseId/{id}")
    public ResponseEntity<CoursesModel> addNewMaterialByCid(@PathVariable("id") String id,@RequestBody CourseMaterialsMappingWithCourse cr){
        return this.services.addNewMaterialByCourseId(cr,id);
    }
    @PostMapping("/RemoveMaterialByCourseId/{id}")
    public ResponseEntity<CoursesModel> reomveNewMaterialByCid(@PathVariable("id") String id,@RequestBody CourseMaterialsMappingWithCourse cr){
        return this.services.removeCourseById(cr,id);
    }


}
