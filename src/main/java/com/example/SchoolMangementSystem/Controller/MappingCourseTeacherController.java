package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.Entities.MappingsCourseTeachers;
import com.example.SchoolMangementSystem.Services.MappingCourseTeacherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/MappingsCourseTeachers")
public class MappingCourseTeacherController {

    @Autowired
    public MappingCourseTeacherServices services;
    @PostMapping("/addNewMappings")
    public ResponseEntity<MappingsCourseTeachers> addNewMapping(@RequestBody MappingsCourseTeachers mappingsCourseTeachers){
        return this.services.addNewMapping(mappingsCourseTeachers);
    }

    @GetMapping("/allmappingsbyClassId/{id}")
    public ResponseEntity<List<MappingsCourseTeachers>> getMappingsbyClassId(@PathVariable("id") String id){
        return this.services.getalldataByClassId(id);
    }

    @GetMapping("/allmappings/{id}")
    public ResponseEntity<MappingsCourseTeachers> getMappingById(@PathVariable("id") String id){
        return this.services.getMappingById(id);
    }

    @GetMapping("deleteMappingById/{id}")
    public ResponseEntity<MappingsCourseTeachers> deleteMappingById(@PathVariable("id")String id){

        return this.services.deleteMappingById(id);
    }
}
