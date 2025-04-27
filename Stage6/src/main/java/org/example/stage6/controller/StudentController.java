package org.example.stage6.controller;

import jakarta.validation.Valid;
import org.example.stage6.dto.StudentDto;
import org.example.stage6.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for student operations
 * Now delegates DTO conversion to the service layer
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Get all students
     */
    @GetMapping("/getAllStudents")
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Get a student by ID
     */
    @GetMapping("/getStudent/{id}")
    public StudentDto getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    /**
     * Add a new student
     * Uses @Valid to validate a student according to Jakarta Validation constraints
     * Returns ResponseEntity with 201 Created status and location header
     */
    @PostMapping("/addStudent")
    public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto added = studentService.addStudent(studentDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(added.getId())
                .toUri();

        return ResponseEntity.created(location).body(added);
    }

    /**
     * Update a student
     * Uses @Valid to validate student according to Jakarta Validation constraints
     */
    @PutMapping("/updateStudent/{id}")
    public StudentDto updateStudent(@Valid @RequestBody StudentDto studentDto, @PathVariable Long id) {
        return studentService.updateStudent(studentDto, id);
    }

    /**
     * Delete a student
     * Returns 204 No Content
     */
    @DeleteMapping("/deleteStudent/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}