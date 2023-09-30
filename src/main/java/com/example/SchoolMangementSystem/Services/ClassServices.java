package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.DTOS.ClassStudentChangeRequest;
import com.example.SchoolMangementSystem.DTOS.TeacherClassAssignRequest;
import com.example.SchoolMangementSystem.Dao.*;
import com.example.SchoolMangementSystem.Entities.*;
import com.example.SchoolMangementSystem.ErrorHandler.GlobalExceptionClass;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassServices {

    @Autowired
    public ClassDao classDao;
    public ResponseEntity<ClassModel> addNewClass(ClassModel classModel){
       try{
           ClassModel classModel1=this.classDao.save(classModel);
           return ResponseEntity.status(HttpStatus.OK)
                   .body(classModel1);
       }
       catch (Exception e){
           throw new GlobalExceptionClass(e.getMessage(),HttpStatus.BAD_REQUEST);
       }
    }

    public ResponseEntity<List<ClassModel>> getallclasses(){
        List<ClassModel> classModels=this.classDao.findAll();

        Collections.sort(classModels, new Comparator<ClassModel>() {
            @Override
            public int compare(ClassModel o1, ClassModel o2) {
                return o1.getClassId().compareTo(o2.getClassId());
            }
        });
        return ResponseEntity.status(HttpStatus.OK)
                .body(classModels);
    }

    public ResponseEntity<ClassModel> getClassById(String id){
        Optional<ClassModel> classModel=this.classDao.findById(id);
        if(classModel.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(classModel.get());
        }
        else{
            throw new GlobalExceptionClass("NOT_FOUND_DATA",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ClassModel> deleteClassById(String id){
        Optional<ClassModel> classModel=this.classDao.findById(id);

        if(classModel.isPresent()){
            ClassModel classModel1=new ClassModel();
            classModel1.setClassId(classModel.get().getClassId());
            this.classDao.deleteClassesDatafromTeacherClasses(classModel.get().getClassId());
            this.classDao.deleteClassesDataFromCoursesClass(classModel.get().getClassId());

            this.classDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(classModel1);
        }
        else{
            throw new GlobalExceptionClass("DATA_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }
    @Autowired
    public StudentDao studentDao;
    public int flag=0;

    public int checkIfstudentAtreadyPresent(String id){
        flag=0;
        List<ClassModel> classModels=new ArrayList<>();
        classModels=this.classDao.findAll();
        classModels.stream().forEach(e->{
            e.getStudentModels().stream().forEach(x->{
                if(x.getStudentId().equals(id)){
                    flag=1;
                }
            });
        });
        return flag;
    }

    public ResponseEntity<ClassModel> addNewStudent(ClassStudentChangeRequest cr){
        Optional<StudentModel> studentModel=this.studentDao.findById(cr.getStudentId());
        Optional<ClassModel> classModel=this.classDao.findById(cr.getClassId());
        if(studentModel.isPresent()==false){
            throw new GlobalExceptionClass("STUDENT_NOT_FOUND_WITH_ID",HttpStatus.NOT_FOUND);

        }
        else if(classModel.isPresent()==false){
            throw new GlobalExceptionClass("CLASS_NOT_FOUND_WITH_ID",HttpStatus.NOT_FOUND);

        }
        else if(this.checkIfstudentAtreadyPresent(cr.getStudentId())==1){
            throw new GlobalExceptionClass("STUDENT_ALREADY_ASSIGNED_TO_OTHER_CLASS",HttpStatus.BAD_REQUEST);
        }
        else {
            List<StudentModel> studentModels=new ArrayList<>();
            studentModels=classModel.get().getStudentModels();
            studentModels.add(studentModel.get());
            classModel.get().setStudentModels(studentModels);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.classDao.save(classModel.get()));
        }

    }
    public int checkIfStudentPresentInGivenClass(String classId, String stuId){
        flag=0;
        Optional<ClassModel> classModel=this.classDao.findById(classId);
        classModel.get().getStudentModels().stream()
                .forEach(e->{
                    if(e.getStudentId().equals(stuId)){
                     flag=1;
                    }
                });
        return flag;

    }

    public ResponseEntity<ClassModel> removeStudentFromClass(ClassStudentChangeRequest cr){
        Optional<StudentModel> studentModel=this.studentDao.findById(cr.getStudentId());
        Optional<ClassModel> classModel=this.classDao.findById(cr.getClassId());
        if(studentModel.isPresent()==false){
            throw new GlobalExceptionClass("STUDENT_WITH_ID_NOT_PRESENT",HttpStatus.NOT_FOUND);
        }
        else if(classModel.isPresent()==false){
            throw new GlobalExceptionClass("CLASS_WITH_ID_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        else if(this.checkIfStudentPresentInGivenClass(cr.getClassId(),cr.getStudentId())==0){
            throw new GlobalExceptionClass("STUDENT_DOES_NOT_PRESENT_IN_GIVEN_CLASS",HttpStatus.NOT_FOUND);

        }
        else{
            List<StudentModel> studentModels=new ArrayList<>();
            studentModels=classModel.get().getStudentModels();
            studentModels.remove(studentModel.get());
            classModel.get().setStudentModels(studentModels);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.classDao.save(classModel.get()));

        }
    }

    public void CheckIfCourseExist(String courseId, String classId){
        flag=0;
        Optional<ClassModel> classModel=this.classDao.findById(classId);
        if(classModel.isPresent()){
            classModel.get().getCoursesModelList().stream()
                    .forEach(e->{
                        if(e.getCourseId().equals(courseId)){
                            throw new GlobalExceptionClass("COURSE_ALREADY_PRESENT",HttpStatus.INTERNAL_SERVER_ERROR);
                        }

                    });
        }
        else{
            throw new GlobalExceptionClass("CLASS_DOES_NOT_EXIST",HttpStatus.NOT_FOUND);
        }



    }
    @Autowired
    public CoursesDao coursesDao;

    public ResponseEntity<ClassModel> addNewCourse(ClassModel classModel,String courseId){
Optional<CoursesModel> coursesModel=this.coursesDao.findById(courseId);
if(coursesModel.isPresent()){
    this.CheckIfCourseExist(courseId, classModel.getClassId());
    this.classDao.addNewCourseByQuery(classModel.getClassId(),courseId);
    ClassModel c1=new ClassModel();
    c1=this.classDao.getClassByItsId(classModel.getClassId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(this.classDao.save(c1));

}
else{
    throw new GlobalExceptionClass("COURSE_DOES_NOT_EXIST",HttpStatus.NOT_FOUND);
}
    }

    public List<CoursesModel> removeCoursefromList(String courseId ,List<CoursesModel> coursesModelList){
       coursesModelList= coursesModelList.stream().filter(e->e.getCourseId()!=courseId)
                .collect(Collectors.toList());

        return coursesModelList;
    }

    public ResponseEntity<ClassModel> removeCourseById(ClassModel classModel,String courseId){
        Optional<CoursesModel> coursesMode=this.coursesDao.findById(courseId);
        if(coursesMode.isPresent()){
          this.classDao.removeCourseByQuery(classModel.getClassId(),courseId);
          ClassModel c1=new ClassModel();
          c1=this.classDao.getClassByItsId(classModel.getClassId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.classDao.save(c1));

        }
        else{
            throw new GlobalExceptionClass("COURSE_DOES_NOT_EXIST",HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public TeacherDao teacherDao;

    @Autowired
    public TeacherCourseDataForClassDao teacherCourseDataForClassDao;

    public int CheckIfTeacherPresentInClass(ClassModel classModel, String teacherId){
       flag=0;
       classModel.getTeacherModelList().stream()
               .forEach(e->{
                   if(e.getTeacherId().equals(teacherId)){
                       flag=1;
                   }
               });

        return flag;
    }



    @Transactional
    public ResponseEntity<ClassModel> addNewTeacherForClass(TeacherClassAssignRequest request){
        Optional<TeacherModel> teacherModel=this.teacherDao.findById(request.getTeacherId());
        Optional<ClassModel> classModel=this.classDao.findById(request.getClassId());
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(request.getCourseId());

        if(classModel.isPresent()==false){
            throw new GlobalExceptionClass("CLASS DOES NOT EXIST",HttpStatus.NOT_FOUND);
        }
        else if(teacherModel.isPresent()==false){
            throw  new GlobalExceptionClass("TEACHER NOT PRESENT FOR ASSIGNMENT",HttpStatus.NOT_FOUND);
        }
        else if(coursesModel.isPresent()==false){
            throw new GlobalExceptionClass("COURSE DOES NOT EXIST",HttpStatus.NOT_FOUND);
        }
        else if(this.CheckIfTeacherPresentInClass(classModel.get(),teacherModel.get().getTeacherId())==1){
            throw new GlobalExceptionClass("TEACHER ALREADY ASSIGN TO CLASS",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
          this.teacherDao.addTeacherQueryforClass(classModel.get().getClassId(),teacherModel.get().getTeacherId());
          ClassModel c1=new ClassModel();
          c1=this.classDao.getClassByItsId(classModel.get().getClassId());
            TeacherCourseDataForClass dataForClass=new TeacherCourseDataForClass();
            dataForClass.setClassModel(classModel.get());
            dataForClass.setTeacherModel(teacherModel.get());
            dataForClass.setCoursesModel(coursesModel.get());
            this.teacherCourseDataForClassDao.save(dataForClass);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(c1);

        }
    }
    String k=null;
   public String getTeacherCourseIdToBedelete(String classId, String courseId, String teacherId){
        List<TeacherCourseDataForClass> dataForClasses=new ArrayList<>();
        dataForClasses=this.teacherCourseDataForClassDao.findAll();
        k=null;
        dataForClasses.stream().forEach(e->{
            if(e.getTeacherModel().getTeacherId().equals(teacherId)
            && e.getClassModel().getClassId().equals(classId) &&
            e.getCoursesModel().getCourseId().equals(courseId)){
                k= e.getDataId();
            }
        });

       return k;
   }

    @Transactional
    public ResponseEntity<ClassModel> removeTeacherFromClass(TeacherClassAssignRequest request){
        Optional<TeacherModel> teacherModel=this.teacherDao.findById(request.getTeacherId());
        Optional<ClassModel> classModel=this.classDao.findById(request.getClassId());
        Optional<CoursesModel> coursesModel=this.coursesDao.findById(request.getCourseId());
        if(classModel.isPresent()==false){
            throw new GlobalExceptionClass("CLASS DOES NOT EXIST",HttpStatus.NOT_FOUND);
        }
        else if(teacherModel.isPresent()==false){
            throw  new GlobalExceptionClass("TEACHER NOT PRESENT FOR ASSIGNMENT",HttpStatus.NOT_FOUND);
        }
        else if(this.CheckIfTeacherPresentInClass(classModel.get(),teacherModel.get().getTeacherId())==0){
            throw new GlobalExceptionClass("TEACHER NOT PRESENT IN CLASS",HttpStatus.NOT_FOUND);

        }
        else{
           this.teacherDao.removeTeacherQueryForClass(classModel.get().getClassId(),teacherModel.get().getTeacherId());
           ClassModel c1=new ClassModel();
           c1=this.classDao.getClassByItsId(classModel.get().getClassId());
           String x=this.getTeacherCourseIdToBedelete(classModel.get().getClassId(),coursesModel.get().getCourseId(),teacherModel.get().getTeacherId());
            this.teacherCourseDataForClassDao.deleteById(x);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(c1);

        }
    }


}
