package com.example.demo.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.StudentRegistrationRepository;
import com.example.demo.bean.StudentRegistration;

@Service
public class StudentRegistrationService {

	@Autowired
	StudentRegistrationRepository repository;
	
	public String registerNewStudent(StudentRegistration newStudent) {
		if(repository.existsByStudentMobileNumber(newStudent.getStudentMobileNumber())) {
			return "The Student with this Mobile Number is Already Exist";
		}
		repository.save(newStudent);	
		return "Student save Successfully";
	}

	public String deleteStudent(Long id) {
		if(repository.existsById(id)) {
			repository.deleteById(id);
			return "Student Deleted Successfully";
		}
		return "Student with id "+ id + " is not Present in the Database";
		
	}
	
	public StudentRegistration getStudnetDetailsById(Long id) {
		 Optional<StudentRegistration> student = repository.findById(id);
		    
		    if (student.isPresent()) {
		        return student.get();
		    } else {
		        throw new IllegalArgumentException("Student not found with ID: " + id);
		    }
		}
	
	
		public ResponseEntity<?> updateStudent(Long id, StudentRegistration updateStudent) {
			
//			if(repository.existsByStudentMobileNumber(updateStudent.getStudentMobileNumber())) {
//				return "The Student with this Mobile Number is Already Exist";
//			}
			if (repository.existsByStudentMobileNumber(updateStudent.getStudentMobileNumber())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Student with this Mobile Number Already Exists");
	        }
//		
			StudentRegistration student = repository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));

			student.setStudentName(updateStudent.getStudentName());
			student.setStudentMobileNumber(updateStudent.getStudentMobileNumber());
			student.setAddress(updateStudent.getAddress());

			StudentRegistration updatedStudent = repository.save(student);
	        
	        return ResponseEntity.ok(updatedStudent);
		}

	/*public StudentRegistration updateStudent(Long id, StudentRegistration updateStudent) {
		    StudentRegistration student = repository.findById(id)
		            .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));

		    student.setStudentName(updateStudent.getStudentName());
		    student.setStudentMobileNumber(updateStudent.getStudentMobileNumber());
		    student.setAddress(updateStudent.getAddress());

		    return repository.save(student);
		}*/

	/*public void registerNewStudent(StudentRegistration newStudent) {
		repository.save(newStudent);
	}*/

	/*public StudentRegistration getStudentByMobileNumber(Long StudentMobileNumber) {
				return repository.findByStudentMobileNumber(StudentMobileNumber).orElse(null);
	}*/
	

}
