package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.DTOS.FileResponse;
import com.example.SchoolMangementSystem.Entities.FileEntity;
import com.example.SchoolMangementSystem.Services.Fileservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class FileController {
    @Autowired
    public Fileservices fileservices;

    @GetMapping("/courses/download/downloadfile/{id}")
    public ResponseEntity<Resource> downLoadFile(@PathVariable String id) throws Exception {
        FileEntity attachment=null;
        attachment=this.fileservices.getAttachmentById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\""
                                +attachment.getFileName()+"\"")
                .body(new ByteArrayResource(attachment.getData()));


    }

    @PostMapping("/upload/file")
    public FileResponse uploadNewFile(@RequestParam("file") MultipartFile file) throws Exception {
        return this.fileservices.uploadFile(file);
    }



}
