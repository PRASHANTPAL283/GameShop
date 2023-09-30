package com.example.SchoolMangementSystem.Dao;


import com.example.SchoolMangementSystem.Entities.FileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface FileDao extends JpaRepository<FileEntity,String> {
    public FileEntity findByFileId(String id);
}
