package com.example.SchoolMangementSystem.Controller;

import com.example.SchoolMangementSystem.Entities.Timetable;
import com.example.SchoolMangementSystem.Services.TimeTableServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/timetable")
public class TimeTableController {
    @Autowired
    public TimeTableServices timeTableServices;
    @PostMapping("/addNewTimeTable")
    public ResponseEntity<Timetable> addNewTimeTable(@RequestBody Timetable timetable){
      return this.timeTableServices.addNewTimeTable(timetable);
    }
    @GetMapping("/allTimetables")
    public ResponseEntity<List<Timetable>> getallTimetables(){
        return this.timeTableServices.getallTimeTables();
    }

    @GetMapping("/allTimetables/{id}")
    public ResponseEntity<Timetable> getTimeTableById(@PathVariable("id") String id){
        return this.timeTableServices.getTimeTableById(id);
    }
    @GetMapping("/getTimeTablesByClassId/{id}")
    public ResponseEntity<List<Timetable>> getTimeTablesByClassId(@PathVariable("id") String id){
        return this.timeTableServices.getTimeTableByClassId(id);
    }

    @GetMapping("/deleteTimeTableById/{id}")
    public ResponseEntity<Timetable> deleteTimeTableById(@PathVariable("id") String id){
        return this.timeTableServices.deleteTimeTableById(id);
    }

}
