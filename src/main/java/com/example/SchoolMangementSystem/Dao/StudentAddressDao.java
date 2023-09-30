package com.example.SchoolMangementSystem.Dao;

import com.example.SchoolMangementSystem.Entities.StudentAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentAddressDao extends JpaRepository<StudentAddress,String> {

    @Modifying
    @Query("delete from StudentAddress u where u.addId=:id")
    public void deleteAddressByAddId(@Param("id") String id);
}
