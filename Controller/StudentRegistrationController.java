package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.StudentRegistrationService;
import com.example.demo.bean.StudentRegistration;

@RestController
public class StudentRegistrationController {
	
	@Autowired
	StudentRegistrationService service;
	
	
	@PostMapping("/register/studentdetails")
	public ResponseEntity<String> registerNewStudent(@RequestBody StudentRegistration newStudent){
		//StudentRegistration student = new StudentRegistration();
		//service.registerNewStudnet(newStudent);
		//return ResponseEntity.status(HttpStatus.CREATED).body("Student Register Successfully");
		
		String result = service.registerNewStudent(newStudent);
        if (result.contains("already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
	} 
	
	/*@PostMapping("/register/studentdetails")
	public ResponseEntity<String> registerNewStudent(@RequestBody StudentRegistration newStudent){
	
		
		service.registerNewStudent(newStudent);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
		
	}*/
	
	
	
	@DeleteMapping("/delete/studentdetails/{id}")
	public String deleteStudentRegistration(@PathVariable Long id){
		return service.deleteStudent(id);
		
	}
	
	@GetMapping("Studentdetails/{id}")
	public StudentRegistration getStudentRegistrationDetailsById(@PathVariable Long id) {
		StudentRegistration student = service.getStudnetDetailsById(id);
		return(student);
	}
	
	
	@PutMapping("update/studnet/{id}")
	public ResponseEntity<?> updateStudnetDetails(@PathVariable Long id, @RequestBody StudentRegistration updateStudent) {
		return service.updateStudent(id,updateStudent);
		
	}
	/*@PutMapping("update/studnet/{id}")
	public StudentRegistration updateStudnetDetails(@PathVariable Long id, @RequestBody StudentRegistration updateStudent) {
		return service.updateStudent(id,updateStudent);
	}*/
	
//	@GetMapping("getStudentByMobile/{studentMobileNumber}")
//	public ResponseEntity<StudentRegistration> getStudentByMobileNumber( @PathVariable Long studentMobileNumber){
//		StudentRegistration student = service.getStudentByMobileNumber(studentMobileNumber);
//		return student != null? ResponseEntity.ok(student):ResponseEntity.notFound().build();		
//	}
	
	

}
