package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.Dao.StudentAddressDao;
import com.example.SchoolMangementSystem.Dao.StudentDao;
import com.example.SchoolMangementSystem.Entities.StudentAddress;
import com.example.SchoolMangementSystem.Entities.StudentModel;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentAddressServices {
    @Autowired
    public StudentAddressDao studentAddressDao;

    @Autowired
    public StudentDao studentDao;

    public ResponseEntity<StudentAddress> addNewAddress(StudentAddress studentAddress){
        try{
            StudentAddress studentAddress1=this.studentAddressDao.save(studentAddress);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(studentAddress1);
        }
        catch (Exception e){
            throw new GlobalExceptionClass("ADDING_ADDRESS_FAILED",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<StudentAddress>> getalladdressByStudentId(String id){
        List<StudentAddress> studentAddresses=new ArrayList<>();
        this.studentAddressDao.findAll().stream()
                .forEach(e->{
                    if(e.getStudentModel().getStudentId().equals(id)){
                        studentAddresses.add(e);
                    }
                });
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentAddresses);
    }

    public ResponseEntity<StudentAddress> getAddressBYId(String id){
        Optional<StudentAddress> studentAddress=this.studentAddressDao.findById(id);
        if(studentAddress.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(studentAddress.get());
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StudentAddress> addNewAddressByStudent(StudentAddress studentAddress, String studentId){
        Optional<StudentModel> studentModel=this.studentDao.findById(studentId);
        if(studentModel.isPresent()){
            studentAddress.setStudentModel(studentModel.get());
            return this.addNewAddress(studentAddress);
        }

        else {
            throw new GlobalExceptionClass("STUDENT_NOT_FOUNDED",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StudentAddress> deleteStudentAddrressById(String id, String studentid){
        Optional<StudentModel> studentModel=this.studentDao.findById(studentid);
        if(studentModel.isPresent()){
            Optional<StudentAddress> studentAddress=Optional.of(this.getAddressBYId(id).getBody());
            if(studentAddress.isPresent()){
                this.studentAddressDao.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(studentAddress.get());
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
