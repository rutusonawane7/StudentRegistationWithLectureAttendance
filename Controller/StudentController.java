package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.StudentService;
import com.example.demo.bean.Student;

import org.springframework.core.io.Resource;

@RestController
public class StudentController {

	@Autowired
	StudentService service;

	@PostMapping("/save/student")
	public ResponseEntity<Student> addNewStudent(
			@RequestPart("name") String name, 
			@RequestPart("photo") MultipartFile file) {

		Student student = new Student();
		student.setStudent_name(name);

				
		service.registerNewStudent(student, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(student);
	}
	
	@GetMapping("/retrive/student/{id}")
	public ResponseEntity<Resource> getStudentPhoto(@PathVariable Long id){
		Resource photo = service.getStudentPhoto(id);
		
		if(photo != null) {
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + photo.getFilename()+ "\"")
					.body(photo);
			
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	@PutMapping("/update/student/{id}")
	public ResponseEntity<String> updateStudent(
			@PathVariable Long
			id,
			@RequestPart("name") String name, 
			@RequestPart("photo") MultipartFile file){
				
				String response = service.updateStudent(id,name,file);
				
					return ResponseEntity.ok(response);
					
				
		}
	@DeleteMapping("/remove/student/{id}")
	public ResponseEntity<String> removeStudentById(@PathVariable Long id){
		String response = service.removeStudentById(id);
		if(response.contains("Successfully")) {
			return ResponseEntity.ok(response);
			
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	
	
	
	
}


