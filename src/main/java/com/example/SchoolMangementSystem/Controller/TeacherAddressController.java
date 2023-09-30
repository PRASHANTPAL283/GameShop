package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.Entities.TeacherAddress;
import com.example.SchoolMangementSystem.Services.TeacherAddressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/teacherAddress")
public class TeacherAddressController {
    @Autowired
    public TeacherAddressServices teacherAddressServices;
    @PostMapping("/addNewAddressByTeacherId/{id}")
    public ResponseEntity<TeacherAddress> addNewAddress(@RequestBody TeacherAddress teacherAddress, @PathVariable("id")String id){
        return this.teacherAddressServices.addNewAddressByTeacher(teacherAddress,id);
    }

    @GetMapping("/alladdressesByTeacherId/{id}")
    public ResponseEntity<List<TeacherAddress>> getalladdress(@PathVariable("id") String id){
        return this.teacherAddressServices.getalladdressByTeacherId(id);
    }

    @GetMapping("/deleteAddressByTeacherId/{id}/{teacherId}")
    public ResponseEntity<TeacherAddress> deleteAddressbyTeacherId(@PathVariable("id") String id,@PathVariable("teacherId")String teacherId){
        return this.teacherAddressServices.deleteTeacherAddrressById(id,teacherId);
    }

    @GetMapping("/getAddressById/{id}")
    public ResponseEntity<TeacherAddress> getAddressByid(@PathVariable("id") String id){
        return this.teacherAddressServices.getAddressBYId(id);
    }


}
