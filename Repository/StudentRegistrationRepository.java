package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.StudentRegistration;

public interface StudentRegistrationRepository extends JpaRepository<StudentRegistration, Long>{

	//boolean findByStudentMobileNumber(Long StudentMobileNumber);
	boolean existsByStudentMobileNumber(Long studentMobileNumber);


}
