package com.example.SchoolMangementSystem.Services;

import com.example.SchoolMangementSystem.DTOS.FileResponse;
import com.example.SchoolMangementSystem.Dao.FileDao;
import com.example.SchoolMangementSystem.Entities.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Fileservices {
    @Autowired
    public FileDao attachRepo;


    public FileResponse uploadFile(MultipartFile file) throws Exception {
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new Exception("invalid path sequence");
            }

            FileEntity attachment=new FileEntity(
                    null,

                    file.getName(),
                    file.getContentType(),
                    file.getBytes()

            );
            attachment= this.attachRepo.save(attachment);

            String downloadURL="http://localhost:8080/courses/download/downloadfile/"+attachment.getFileId();

            FileResponse responseData=new FileResponse(
                    attachment.getFileId(),
                    file.getName(),
                    downloadURL,
                    file.getContentType(),

                    file.getSize()
            );

            return responseData;

        }
        catch (Exception e){
            throw new Exception("file cant be uploaded");

        }

    }
    public FileResponse updateuploadedFile(MultipartFile file , String id) throws Exception {
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                throw new Exception("invalid path sequence");
            }

            FileEntity attachment=new FileEntity(
                    id,

                    file.getName(),
                    file.getContentType(),
                    file.getBytes()

            );
            attachment= this.attachRepo.save(attachment);

            String downloadURL="http://localhost:8080/courses/download/downloadfile/"+attachment.getFileId();

            FileResponse responseData=new FileResponse(
                    attachment.getFileId(),
                    file.getName(),
                    downloadURL,
                    file.getContentType(),

                    file.getSize()
            );

            return responseData;



        }
        catch (Exception e){
            throw new Exception("file cant be uploaded");

        }

    }
    public FileEntity getAttachmentById(String id){
        return this.attachRepo.findByFileId(id);
    }
    public void deleteFileById(String id){
        this.attachRepo.deleteById(id);
    }
}

