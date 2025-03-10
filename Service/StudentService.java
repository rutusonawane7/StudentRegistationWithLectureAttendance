package com.example.demo.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.StudentRepository;
import com.example.demo.bean.Student;

@Service
public class StudentService {
	

	@Autowired
	StudentRepository repository;

	private static final String UPLOAD_DIR = "studentphotos"; // folder chi link

	public void registerNewStudent(Student student, MultipartFile file) {
		
		try {
			long startTime = System.currentTimeMillis();
			
            //  absolute path of the project directory dynamically
            String projectRoot = System.getProperty("user.dir"); // absolute path of current directory

            String fileDirectory = projectRoot + File.separator + UPLOAD_DIR; //in this absolute path + upload_dir 

            
            File directory = new File(fileDirectory); // assign absolute path _ upload_dir to file object just path 
            if (!directory.exists()) { // check that path directory is exist or not
                directory.mkdirs(); // if not create new directory with that path
            }

            //  file path
            String filePath = fileDirectory + File.separator + file.getOriginalFilename();// directory path + original file name
            File destinationFile = new File(filePath);// final path where file will saved
            file.transferTo(destinationFile);// moves uploaded file to final location 

            // Store only relative path in DB (for easier retrieval)
            student.setStudent_photo(UPLOAD_DIR + "/" + file.getOriginalFilename());// assign path to the student_photo
            
            long endTime=System.currentTimeMillis();// end time
			
            System.out.println("removeStudentById execution time: " + (endTime - startTime) + " ms");
             
            
            repository.save(student); // save the student it has name and photo of the student 

        } catch (IOException e) {
            throw new RuntimeException("Error while saving student photo", e);
        }
		
	}
	
	public Resource getStudentPhoto(Long id) {
		
		
		long startTime = System.currentTimeMillis();
		
		
		 Optional<Student> student = repository.findById(id);
		 
		 if(student.isPresent()) {
			 String filePath = student.get().getStudent_photo();
			 try {
				 Path path = Paths.get(filePath);
				 Resource resource = new UrlResource(path.toUri());
				 if(resource.exists() || resource.isReadable()) {
					 
					 long endTime = System.currentTimeMillis();
		
		             System.out.println("getStudentPhoto execution time: " + (endTime - startTime) + " ms");
		             
		             // 4ms,3ms,4ms,4ms 
		             // 4mb - 123ms,4ms,4ms,7ms,5ms
		             
		             
					 return resource;
					 
				 }else {
					 throw new RuntimeException("File not Found or not readable : "+ filePath);
					 
				 } 
				 
			 }catch(Exception e) {
				 throw new RuntimeException("Error retrieving file",e);
			 }
		 }
		return null;
	}
		
		
		
	public String updateStudent(Long id, String name, MultipartFile file) {
		    long startTime = System.currentTimeMillis();

		    Optional<Student> optionalStudent = repository.findById(id);
		    if (optionalStudent.isPresent()) {
		        Student student = optionalStudent.get();
		        student.setStudent_name(name);

		        try {
		            // Get the absolute path of the project directory dynamically
		            String projectRoot = System.getProperty("user.dir"); // Gets project root
		            String fileDirectory = projectRoot + File.separator + UPLOAD_DIR;

		            //  upload directory exists
		            File directory = new File(fileDirectory);
		            if (!directory.exists()) {
		                directory.mkdirs();
		            }

		            // Delete old file if it exists
		            if (student.getStudent_photo() != null) {// check path is exist
		                Files.deleteIfExists(Paths.get(projectRoot + File.separator + student.getStudent_photo()));
		                // delete if exist --- construct path using projectRoot + upload_dir
		            }

		            // Generate new file path
		            String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		            // generate unique id to prevent name conflict _ original file name
		            
		            String filePath = fileDirectory + File.separator + newFileName;// create absolute path 
		            
		            
		            File newFile = new File(filePath);// file object

		            // Save new file
		            file.transferTo(newFile);

		            // Store only the relative path in DB (for easier retrieval)
		            student.setStudent_photo(UPLOAD_DIR + "/" + newFileName);

		            repository.save(student);

		        } catch (IOException e) {
		            throw new RuntimeException("Error while updating student photo", e);
		        }

		        long endTime = System.currentTimeMillis();
		        System.out.println("updateStudent execution time: " + (endTime - startTime) + " ms");

		        return "Student updated successfully!";
		    } else {
		        return "Student with ID " + id + " not found";
		    }
		}
	public String removeStudentById(Long id) {
			
			long startTime = System.currentTimeMillis();
			
			
			if(repository.existsById(id)) { // check id is exist 
				repository.deleteById(id);  // if exist delete it
				
				long endTime=System.currentTimeMillis();// end time
			
					
	            System.out.println("removeStudentById execution time: " + (endTime - startTime) + " ms");
	             
	            
	            // 27,19,15 milliseconds
	            // (595 1st time executio
	            // 4bm - 18ms,12ms
				return "Student Removed Successfully";
				
			}else {
				return "Student with id "+ id +"Not Prsent in Database";
			}
		}
	

}
