package org.example.stage6.controller;

import jakarta.validation.Valid;
import org.example.stage6.dto.StudentDto;
import org.example.stage6.response.StandardResponse;
import org.example.stage6.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for student operations
 * Now delegates DTO conversion to the service layer and returns ResponseEntity<StandardResponse>
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
     * Returns ResponseEntity with StandardResponse and 200 OK status
     */
    @GetMapping("/getAllStudents")
    public ResponseEntity<StandardResponse> getAllStudents() {
        List<StudentDto> studentList = studentService.getAllStudents();
        StandardResponse response = new StandardResponse("success", studentList, null);
        return ResponseEntity.ok(response); // 200 OK
    }

    /**
     * Get a student by ID
     * Returns ResponseEntity with StandardResponse and 200 OK status
     */
    @GetMapping("/getStudent/{id}")
    public ResponseEntity<StandardResponse> getStudent(@PathVariable Long id) {
        StudentDto student = studentService.getStudent(id);
        StandardResponse response = new StandardResponse("success", student, null);
        return ResponseEntity.ok(response); // 200 OK
    }

    /**
     * Add a new student
     * Uses @Valid to validate a student according to Jakarta Validation constraints
     * Returns ResponseEntity with StandardResponse and 201 Created status with location header
     */
    @PostMapping("/addStudent")
    public ResponseEntity<StandardResponse> addStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto added = studentService.addStudent(studentDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(added.getId())
                .toUri();

        StandardResponse response = new StandardResponse("success", added, null);
        return ResponseEntity.created(location).body(response); // 201 Created
    }

    /**
     * Update a student
     * Uses @Valid to validate a student according to Jakarta Validation constraints
     * Returns ResponseEntity with StandardResponse and 200 OK status
     */
    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<StandardResponse> updateStudent(@Valid @RequestBody StudentDto studentDto, @PathVariable Long id) {
        StudentDto updated = studentService.updateStudent(studentDto, id);
        StandardResponse response = new StandardResponse("success", updated, null);
        return ResponseEntity.ok(response); // 200 OK
    }

    /**
     * Delete a student
     * Returns ResponseEntity with 204 No Content status without a response body
     */
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}