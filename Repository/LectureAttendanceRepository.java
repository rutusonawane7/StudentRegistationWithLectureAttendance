package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.LectureAttendance;

public interface LectureAttendanceRepository extends JpaRepository<LectureAttendance, Long> {

	//Optional<LectureAttendance> findByStudentId(Long student_id);

	List<LectureAttendance> findByStudentId(Long studentId);

}

