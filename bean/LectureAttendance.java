package com.example.demo.bean;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LectureAttendance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String lectureTopic;
	private LocalDateTime lectureDate;
	private String notesFilePath;
	
	
	@ManyToOne
    @JoinColumn(name = "student_id", nullable = false) 
    private StudentRegistration student;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLectureTopic() {
		return lectureTopic;
	}
	public void setLectureTopic(String lectureTopic) {
		this.lectureTopic = lectureTopic;
	}
	public LocalDateTime getLectureDate() {
		return lectureDate;
	}
	public void setLectureDate(LocalDateTime lectureDate) {
		this.lectureDate = lectureDate;
	}
	public String getNotesFilePath() {
		return notesFilePath;
	}
	public void setNotesFilePath(String notesFilePath) {
		this.notesFilePath = notesFilePath;
	}
	
	 public StudentRegistration getStudent() {
	        return student;
	    }
	 public void setStudent(StudentRegistration student) {
	        this.student = student;
	    }
	
	 
	 /* 
	 For Mobile Number Store reference this
	https://stackoverflow.com/questions/3483156/whats-the-right-way-to-represent-phone-numbers

	for custom method 
	https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

	for findBymobileNumber() 
	https://stackoverflow.com/questions/76114308/how-to-check-if-record-exists-before-insert-spring-jpa




	*/

	

}
