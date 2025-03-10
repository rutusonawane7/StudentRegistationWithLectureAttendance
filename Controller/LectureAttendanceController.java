package com.example.demo.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.LectureAttendanceService;
import com.example.demo.bean.LectureAttendance;

@RestController
public class LectureAttendanceController {
	
	@Autowired
	LectureAttendanceService service;
	
	
	/*
		without file Post method
		@PostMapping("markattendance/{student_id}")
		public ResponseEntity<String> markAttendance(
			@PathVariable Long student_id, 
			@RequestBody LectureAttendance attendance){
		
		service.saveAttendance(student_id, attendance);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Attendance Submited Successfully");	
	}*/
	@PostMapping("markattendance/{student_id}")
	public ResponseEntity<LectureAttendance> markAttendance(
			@PathVariable Long student_id, 
			@RequestParam  String lectureTopic,
			@RequestParam LocalDateTime lectureDate,
			@RequestPart MultipartFile file){
		
		LectureAttendance attendance = new LectureAttendance();
		attendance.setLectureTopic(lectureTopic);
		attendance.setLectureDate(lectureDate);
		
		service.saveAttendance(student_id,attendance, file);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(attendance);	
	}
	
	@GetMapping("/getNotes/{student_id}")
	public ResponseEntity<Resource> getStudentNotesPhoto(@PathVariable Long student_id ){
		Resource photo = service.getStudentNotesPhoto(student_id);
		
		if(photo != null) {
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + photo.getFilename()+ "\"")
					.body(photo);
			
		}else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@DeleteMapping("/delete/studentnotes/{Student_Id}")
	public ResponseEntity<String> deleteStudentNotes(@PathVariable Long Student_Id){
		
		String response = service.deleteStudentNotes(Student_Id);
		if(response.contains("Successfully")) {
			return ResponseEntity.ok(response);
			
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	
		
	}
	
	@PutMapping("update/notes/{student_id}")
	public ResponseEntity<String> updateStudentNotes(@PathVariable Long student_id, 
			@RequestParam  String lectureTopic,
			@RequestParam LocalDateTime lectureDate,
			@RequestPart MultipartFile file){
		
		
		
		String response = service.updateStudent(student_id,lectureTopic,lectureDate,file);
		
		return ResponseEntity.ok(response);
		
				 
		
		
	}
	
}
