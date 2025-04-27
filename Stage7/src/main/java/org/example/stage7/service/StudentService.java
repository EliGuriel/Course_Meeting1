package org.example.stage7.service;

import org.example.stage7.dto.StudentDto;
import org.example.stage7.exception.AlreadyExists;
import org.example.stage7.exception.NotExists;
import org.example.stage7.exception.StudentIdAndIdMismatch;

import java.util.List;

/**
 * Service interface for student operations
 * Works directly with DTOs to handle both data conversion and business logic
 */
public interface StudentService {
    /**
     * Get all students from the system as DTOs
     * @return List of all students as DTOs
     */
    List<StudentDto> getAllStudents();
    
    /**
     * Get student by ID as DTO
     * @param id The student ID to retrieve
     * @return The found student as DTO
     * @throws NotExists If a student doesn't exist
     */
    StudentDto getStudent(Long id);
    
    /**
     * Add a new student
     * @param studentDto Student data to add (as DTO)
     * @return The added student as DTO
     * @throws AlreadyExists If a student with the same ID already exists
     */
    StudentDto addStudent(StudentDto studentDto);
    
    /**
     * Update an existing student
     * @param studentDto Updated student data (as DTO)
     * @param id The ID from the path parameter
     * @return The updated student as DTO
     * @throws NotExists If a student doesn't exist
     * @throws StudentIdAndIdMismatch If ID in a path doesn't match student ID
     */
    StudentDto updateStudent(StudentDto studentDto, Long id);
    
    /**
     * Delete a student by ID
     * @param id Student ID to delete
     * @throws NotExists If a student doesn't exist
     */
    void deleteStudent(Long id);
}