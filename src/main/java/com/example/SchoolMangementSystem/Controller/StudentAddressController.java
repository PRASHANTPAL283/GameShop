package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.Entities.StudentAddress;

import com.example.SchoolMangementSystem.Services.StudentAddressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/studentAddress")
public class StudentAddressController {
    @Autowired
    public StudentAddressServices services;
    @PostMapping("/addNewAddressByStudentId/{id}")
    public ResponseEntity<StudentAddress> addNewAddress(@RequestBody StudentAddress studentAddress, @PathVariable("id")String id){
        return this.services.addNewAddressByStudent(studentAddress,id);
    }

    @GetMapping("/alladdressesByStudentId/{id}")
    public ResponseEntity<List<StudentAddress>> getalladdress(@PathVariable("id") String id){
        return this.services.getalladdressByStudentId(id);
    }

    @GetMapping("/deleteAddressByStudentId/{id}/{studentId}")
    public ResponseEntity<StudentAddress> deleteAddressbyStudentId(@PathVariable("id") String id,@PathVariable("studentId")String studentId){
        return this.services.deleteStudentAddrressById(id,studentId);
    }

    @GetMapping("/getStudentAddressById/{id}")
    public ResponseEntity<StudentAddress> getAddressByid(@PathVariable("id") String id){
        return this.services.getAddressBYId(id);
    }


}
