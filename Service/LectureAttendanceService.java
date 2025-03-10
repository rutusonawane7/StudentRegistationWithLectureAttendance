package com.example.demo.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.LectureAttendanceRepository;
import com.example.demo.Repository.StudentRegistrationRepository;
import com.example.demo.bean.LectureAttendance;
import com.example.demo.bean.StudentRegistration;

@Service
public class LectureAttendanceService {

	@Autowired 
	LectureAttendanceRepository attendanceRepo;
	
	@Autowired
	StudentRegistrationRepository studentrepo;
	
	private static final String NOTESPHOTO = "NotesPhoto"; // folder chi link

	
	/*
	 		without file Post method

	  public String saveAttendance(Long studentId, LectureAttendance attendance) {
		
        StudentRegistration student = studentrepo.findById(studentId)
        		
            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        attendance.setStudent(student);

        attendanceRepo.save(attendance);

        return "Attendance recorded successfully!";
    }*/



	public void saveAttendance(Long student_id, LectureAttendance attendance, MultipartFile file) {
		
		Optional<StudentRegistration> studentOpt = studentrepo.findById(student_id);
	    if (studentOpt.isEmpty()) {
	        throw new RuntimeException("Student not found with ID: " + student_id);
	    }

	    StudentRegistration student = studentOpt.get(); 

		try {
			String projectRoot = System.getProperty("user.dir"); 
            String fileDirectory = projectRoot + File.separator + NOTESPHOTO; 

            File directory = new File(fileDirectory); 
            if (!directory.exists()) { 
                directory.mkdirs();  }
            
            String filePath = fileDirectory + File.separator + file.getOriginalFilename();
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);

            attendance.setNotesFilePath(NOTESPHOTO + "/" + file.getOriginalFilename());
           
            attendance.setStudent(student);
            attendanceRepo.save(attendance); 
		}
		catch (IOException e) {
	        throw new RuntimeException("Error saving attendance file: " + e.getMessage());
	    }
	}

	
	/*
	  single notes is available
	 
	 public Resource getStudentNotesPhoto(Long student_id)
	{
		Optional<LectureAttendance> attendanceOpt = attendanceRepo.findByStudentId(student_id);
		
		 if (attendanceOpt.isEmpty() || attendanceOpt.get().getNotesFilePath() == null) {
		        throw new RuntimeException("File not found for student ID: " + student_id);
		    }

		    // Convert stored relative path to absolute path
		    String relativeFilePath = attendanceOpt.get().getNotesFilePath();
		    String absoluteFilePath = System.getProperty("user.dir") + File.separator + relativeFilePath;

		    try {
		        Path path = Paths.get(absoluteFilePath);
		        Resource resource = new UrlResource(path.toUri());

		        if (resource.isReadable()) {
		            return resource;
		        } else {
		            throw new RuntimeException("File not readable: " + absoluteFilePath);
		        }
		    } catch (Exception e) {
		        throw new RuntimeException("Error retrieving file", e);
		    }
		}*/
	public Resource getStudentNotesPhoto(Long student_id) {
	    List<LectureAttendance> attendanceList = attendanceRepo.findByStudentId(student_id);

	    if (attendanceList.isEmpty()) {
	        throw new RuntimeException("No attendance records found for student ID: " + student_id);
	    }

	    
	    LectureAttendance latestAttendance = attendanceList.get(attendanceList.size() - 1);

	    
	    String relativeFilePath = latestAttendance.getNotesFilePath();
	    if (relativeFilePath == null) {
	        throw new RuntimeException("No file associated with the latest attendance record.");
	    }

	   
	    String absoluteFilePath = System.getProperty("user.dir") + File.separator + relativeFilePath;

	    try {
	        Path path = Paths.get(absoluteFilePath);
	        Resource resource = new UrlResource(path.toUri());

	        if (resource.isReadable()) {
	            return resource;
	        } else {
	            throw new RuntimeException("File not readable: " + absoluteFilePath);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving file", e);
	    }
	}


	public String deleteStudentNotes(Long student_Id) {
		
	    List<LectureAttendance> attendanceList = attendanceRepo.findByStudentId(student_Id);

	    if (!attendanceList.isEmpty()) { 
	        attendanceRepo.deleteAll(attendanceList); 

	        return "Student Notes Removed Successfully";
	    } else {
	        return "Student with ID " + student_Id + " not present in the database";
	    }
		
	}


	public String updateStudent(Long student_id, String lectureTopic, LocalDateTime lectureDate, MultipartFile file) {
		
		Optional<StudentRegistration> studentOpt = studentrepo.findById(student_id);
	    if (studentOpt.isEmpty()) {
	        throw new RuntimeException("Student not found with ID: " + student_id);
	    }

	    StudentRegistration student = studentOpt.get(); 
	    
	    List<LectureAttendance> attendanceList = attendanceRepo.findByStudentId(student_id);

	    if (attendanceList.isEmpty()) {
	        return "No attendance record found for Student ID: " + student_id;
	    }

	    LectureAttendance attendance = attendanceList.get(0);
	    attendance.setLectureTopic(lectureTopic);
	    attendance.setLectureDate(lectureDate);
	    
	    try {
			String projectRoot = System.getProperty("user.dir"); 
            String fileDirectory = projectRoot + File.separator + NOTESPHOTO; 

            File directory = new File(fileDirectory); 
            if (!directory.exists()) { 
                directory.mkdirs();  }
            
            String filePath = fileDirectory + File.separator + file.getOriginalFilename();
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile);

            attendance.setNotesFilePath(NOTESPHOTO + "/" + file.getOriginalFilename());
           
            attendance.setStudent(student);
            attendanceRepo.save(attendance); 
		}
		catch (IOException e) {
	        throw new RuntimeException("Error saving attendance file: " + e.getMessage());
	    }

		return null;
	}

		
}
	
