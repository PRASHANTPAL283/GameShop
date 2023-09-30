package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.DTOS.ClassDTO;
import com.example.SchoolMangementSystem.Dao.StudentAddressDao;
import com.example.SchoolMangementSystem.Dao.StudentDao;
import com.example.SchoolMangementSystem.Entities.ClassModel;
import com.example.SchoolMangementSystem.Entities.StudentModel;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dhatim.fastexcel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServices {

    @Autowired
    public StudentDao studentDao;
    public ResponseEntity<StudentModel> addNewStudent(StudentModel studentModel){
        try{
            StudentModel student=this.studentDao.save(studentModel);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(student);
        }
        catch (Exception e){
            throw new GlobalExceptionClass(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<StudentModel>> getallstudentsByClassId(String id){
        List<StudentModel> list=new ArrayList<>();
        this.studentDao.findAll().stream().forEach(e->{
            if(e.getClassModel().getClassId().equals(id)){
                list.add(e);
            }
        });
       return ResponseEntity.status(HttpStatus.OK)
               .body(list);
    }

    public ResponseEntity<List<StudentModel>> getallstudents(){
        List<StudentModel> studentModels=new ArrayList<>();
        studentModels=this.studentDao.findAll();
        Collections.sort(studentModels, new Comparator<StudentModel>() {
            @Override
            public int compare(StudentModel o1, StudentModel o2) {
                return o1.getStudentId().compareTo(o2.getStudentId());
            }
        });
       studentModels=studentModels.stream().map(e->{
           ClassDTO classDTO=new ClassDTO();
           e.setClassDTO(this.converClassDTO(e.getClassModel(),classDTO));
           return e;
       }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentModels);
    }

    public ClassDTO converClassDTO(ClassModel classModel, ClassDTO classDTO){
        classDTO.setClassGrade(classModel.getClassGrade());
        classDTO.setClassId(classModel.getClassId());
        classDTO.setSection(classModel.getSection());
        classDTO.setCoursesModelList(classModel.getCoursesModelList());
        return classDTO;
    }

    public ResponseEntity<StudentModel> getStudentById(String id){
        Optional<StudentModel> studentModel=this.studentDao.findById(id);
        if(studentModel.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(studentModel.get());
        }
        else{
            throw new GlobalExceptionClass("The student with "+id+" not found",HttpStatus.NOT_FOUND);
        }
    }
@Autowired
public StudentAddressDao addressDao;
    @Transactional
    public ResponseEntity<StudentModel> deleteStudentById(String id){
        Optional<StudentModel> studentModel=this.studentDao.findById(id);
        if(studentModel.isPresent()){
            StudentModel studentModel1=new StudentModel();
            this.addressDao.deleteById(studentModel.get().getStudentAddress().getAddId());
            this.studentDao.deleteById(id);
            studentModel1.setStudentId(studentModel.get().getStudentId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(studentModel1);
        }
        else{
            throw new GlobalExceptionClass("STUDENT_NOT_FOUNDED",HttpStatus.BAD_REQUEST);
        }
    }

    public List<StudentModel> getStudentsByName(String name,String email){
        return this.studentDao.getstudentbycity(name,email);
    }

    @Transactional
    public void deleteStudentByQueryId(String id){
        Optional<StudentModel> studentModel=this.studentDao.findById(id);
        String addId=studentModel.get().getStudentAddress().getAddId();
        this.studentDao.deletefromstudentCourses(studentModel.get().getStudentId());
        this.addressDao.deleteAddressByAddId(addId);
        this.studentDao.deleteStudentByItsId(id);
    }
    int i=2;
    int j=0;

    public String generateDataToExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("student_id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("city");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("student_email");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("student_name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("class_id");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        List<StudentModel> studentModelList=new ArrayList<>();
        studentModelList=this.studentDao.findAll();

        studentModelList.stream().forEach(e->{
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(e.getStudentId());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(e.getCity());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(e.getStudentEmail());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(e.getStudentName());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(e.getClassModel().getClassId());
            cell.setCellStyle(style);
            i=i+1;



        });


        File currDir = new File("D:\\ExcelData\\StudentRecords");
        String path = currDir.getAbsolutePath();
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yy");
        String dr1=format.format(date);
        long time=System.currentTimeMillis();

        String sr1= String.valueOf(time);
        String fileLocation = path + sr1+".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();


        return "done";
    }




}
