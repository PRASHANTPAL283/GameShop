package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.Dao.TeacherAddressDao;
import com.example.SchoolMangementSystem.Dao.TeacherDao;
import com.example.SchoolMangementSystem.Entities.TeacherAddress;
import com.example.SchoolMangementSystem.Entities.TeacherModel;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherAddressServices {
    @Autowired
    public TeacherAddressDao teacherAddressDao;
    @Autowired
    public TeacherDao teacherDao;
    public ResponseEntity<TeacherAddress> addNewAddress(TeacherAddress teacherAddress){
        try{
            TeacherAddress teacherAddress1=this.teacherAddressDao.save(teacherAddress);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(teacherAddress1);
        }
        catch (Exception e){
            throw new GlobalExceptionClass("ADDING_ADDRESS_FAILED",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<TeacherAddress>> getalladdressByTeacherId(String id){
        List<TeacherAddress> teacherAddresses=new ArrayList<>();
        this.teacherAddressDao.findAll().stream()
                .forEach(e->{
                    if(e.getTeacherModel().getTeacherId().equals(id)){
                        teacherAddresses.add(e);
                    }
                });
        return ResponseEntity.status(HttpStatus.OK)
                .body(teacherAddresses);
    }

    public ResponseEntity<TeacherAddress> getAddressBYId(String id){
        Optional<TeacherAddress> teacherAddress=this.teacherAddressDao.findById(id);
        if(teacherAddress.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(teacherAddress.get());
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<TeacherAddress> addNewAddressByTeacher(TeacherAddress teacherAddress, String teacherId){
      Optional<TeacherModel> teacherModel=this.teacherDao.findById(teacherId);
      if(teacherModel.isPresent()){
          teacherAddress.setTeacherModel(teacherModel.get());
          return this.addNewAddress(teacherAddress);
      }
      else {
          throw new GlobalExceptionClass("TEACHER_NOT_FOUNDED",HttpStatus.NOT_FOUND);
      }
    }

    public ResponseEntity<TeacherAddress> deleteTeacherAddrressById(String id, String teacherid){
        Optional<TeacherModel> teacherModel=this.teacherDao.findById(teacherid);
        if(teacherModel.isPresent()){
           Optional<TeacherAddress> teacherAddress=Optional.of(this.getAddressBYId(id).getBody());
           if(teacherAddress.isPresent()){
               this.teacherAddressDao.deleteById(id);
               return ResponseEntity.status(HttpStatus.OK)
                       .body(teacherAddress.get());
           }
           else{
               throw new GlobalExceptionClass("ADDRESS_NOT_FOUND",HttpStatus.NOT_FOUND);
           }


        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }




}
