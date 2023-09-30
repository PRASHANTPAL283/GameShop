package com.example.SchoolMangementSystem.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileResponse {
    public String ImageId;
    public String fileName;
    public String downloadURL;
    public String fileType;
    public long fileSize;

}
