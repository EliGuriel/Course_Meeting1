package org.example.stage6.mapper;

import org.example.stage6.dto.StudentDto;
import org.example.stage6.model.Student;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Student entity and StudentDto
 */
@Component
public class StudentMapper {
    
    /**
     * Convert from DTO to Entity
     * @param dto The DTO to convert
     * @return The converted entity or null if dto is null
     */
    public Student toEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }
        
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setAge(dto.getAge());
        
        return student;
    }
    
    /**
     * Convert from Entity to DTO
     * @param entity The entity to convert
     * @return The converted DTO or null if entity is null
     */
    public StudentDto toDto(Student entity) {
        if (entity == null) {
            return null;
        }
        
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(entity.getAge());
        
        return dto;
    }
    
    /**
     * Update entity from DTO
     * @param entity The entity to update
     * @param dto The DTO with new data
     */
    public void updateEntityFromDto(Student entity, StudentDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAge(dto.getAge());
    }
}