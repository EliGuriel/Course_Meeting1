package org.example.stage6.service;

import org.example.stage6.dto.StudentDto;
import org.example.stage6.exception.AlreadyExists;
import org.example.stage6.exception.NotExists;
import org.example.stage6.exception.StudentIdAndIdMismatch;
import org.example.stage6.mapper.StudentMapper;
import org.example.stage6.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the StudentService interface
 * Handles both data conversion and business logic
 */
@Service
public class StudentServiceImpl implements StudentService {

    // In a real application, this would be replaced with a repository
    private final List<Student> students = new ArrayList<>(Arrays.asList(
        new Student(1L, "Alice", "Moskovitz", 21.3),
        new Student(2L, "Bob", "Smith", 22.3),
        new Student(3L, "Charlie", "Brown", 23.3),
        new Student(4L, "David", "Miller", 24.3)
    ));
    
    private final StudentMapper studentMapper;
    
    // Inject the mapper through constructor
    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        // Convert all entities to DTOs
        return students.stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudent(Long id) {
        // Find entity and convert to DTO
        Student student = findStudentById(id);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        // Convert DTO to entity
        Student student = studentMapper.toEntity(studentDto);
        
        // Check if student exists
        if (students.stream().anyMatch(s -> s.getId().equals(student.getId()))) {
            throw new AlreadyExists("Student with id " + student.getId() + " already exists");
        }
        
        // Add student and convert back to DTO
        students.add(student);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Long id) {
        // Convert DTO to entity
        Student student = studentMapper.toEntity(studentDto);
        
        // Check if a student exists
        if (students.stream().noneMatch(s -> s.getId().equals(id))) {
            throw new NotExists("Student with id " + id + " does not exist");
        }
        
        // Check ID match
        if (!student.getId().equals(id)) {
            throw new StudentIdAndIdMismatch("Student with id " + id + " mismatch with body id " + student.getId());
        }
        
        // Update student
        students.stream()
            .filter(s -> s.getId().equals(student.getId()))
            .forEach(s -> {
                s.setFirstName(student.getFirstName());
                s.setLastName(student.getLastName());
                s.setAge(student.getAge());
            });
            
        // Convert back to DTO
        return studentMapper.toDto(student);
    }

    @Override
    public void deleteStudent(Long id) {
        // Check if student exists
        if (students.stream().noneMatch(s -> s.getId().equals(id))) {
            throw new NotExists("Student with id " + id + " does not exist");
        }
        
        // Delete student
        students.removeIf(s -> s.getId().equals(id));
    }
    
    /**
     * Helper method to find a student by ID
     * @param id Student ID
     * @return Student entity
     * @throws NotExists If a student doesn't exist
     */
    private Student findStudentById(Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotExists("Student with id " + id + " does not exist"));
    }
}