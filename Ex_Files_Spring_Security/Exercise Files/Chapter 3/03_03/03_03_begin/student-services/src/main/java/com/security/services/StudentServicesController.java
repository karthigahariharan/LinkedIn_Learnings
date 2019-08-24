package com.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Frank P. Moley III.
 */
@RestController
@RequestMapping("/students")
public class StudentServicesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServicesController.class);

    private final StudentRepository repository;

    public StudentServicesController(StudentRepository repository){
        super();
        this.repository = repository;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return new ArrayList<>(this.repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody StudentModel model){
        Student student = this.repository.save(model.translateModelToStudent());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(student.getId()).toUri();
        return ResponseEntity.created(location).body(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        Optional<Student> student = this.repository.findById(id);
        if(student.isPresent()){
            return student.get();
        }
        throw new StudentNotFoundException("Student not found with id: " + id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody StudentModel model){
        Optional<Student> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        Student student = model.translateModelToStudent();
        student.setId(id);
        return this.repository.save(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteStudent(@PathVariable Long id){
        Optional<Student> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        this.repository.deleteById(id);
    }
}
