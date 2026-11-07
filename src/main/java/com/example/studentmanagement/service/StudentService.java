package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.DuplicateEmailException;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student addStudent(Student student) {
        if (repository.existsByEmail(student.getEmail())) {
            throw new DuplicateEmailException("Email already in use: " + student.getEmail());
        }
        student.setId(null); // ensure a new record is created
        return repository.save(student);
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    public Student updateStudent(Long id, Student details) {
        Student existing = getStudentById(id);
        if (repository.existsByEmailAndIdNot(details.getEmail(), id)) {
            throw new DuplicateEmailException("Email already in use: " + details.getEmail());
        }
        existing.setName(details.getName());
        existing.setEmail(details.getEmail());
        existing.setCourse(details.getCourse());
        existing.setAge(details.getAge());
        return repository.save(existing);
    }

    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        repository.delete(existing);
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourse(String course) {
        return repository.findByCourseIgnoreCase(course);
    }
}
