package com.security.app.service;

import com.security.app.domain.Student;
import com.security.app.domain.StudentModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Frank P. Moley III.
 */
@Service
public class StudentService {
    private static final String STUDENTS = "/students";
    private static final String SLASH = "/";

    @Value("${student.service.url}")
    private String studentServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Student> getAllStudents(){
        String url = studentServiceUrl + STUDENTS;
        System.out.println(url);
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<Student>>() { }).getBody();
    }

    public Student addStudent(StudentModel studentModel){
        String url = studentServiceUrl + STUDENTS;
        HttpEntity<StudentModel> request = new HttpEntity<>(studentModel, null);
        return this.restTemplate.exchange(url, HttpMethod.POST, request, Student.class).getBody();
    }

    public Student getStudent(long id) {
        String url = studentServiceUrl + STUDENTS + SLASH + id;
        HttpEntity<String> request = new HttpEntity<>(null, null);
        return this.restTemplate.exchange(url, HttpMethod.GET, request, Student.class).getBody();
    }

    public Student updateStudent(long id, StudentModel studentModel) {
        System.out.println(studentModel);
        String url = studentServiceUrl + STUDENTS + SLASH + id;
        HttpEntity<StudentModel> request = new HttpEntity<>(studentModel, null);
        return this.restTemplate.exchange(url, HttpMethod.PUT, request, Student.class).getBody();
    }
}
