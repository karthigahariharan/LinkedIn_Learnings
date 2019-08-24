package com.security.app;

import com.security.app.domain.Student;
import com.security.app.domain.StudentModel;
import com.security.app.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Frank P. Moley III.
 */
@Controller
@RequestMapping("/")
public class StudentAppController {

    private final StudentService studentService;

    public StudentAppController(StudentService studentService){
        super();
        this.studentService = studentService;
    }

    @GetMapping(value={"/", "/index"})
    public String getHomePage(Model model){

        return "index";
    }

    @GetMapping(value="/login")
    public String getLoginPage(Model model){
        return "login";
    }

    @GetMapping(value="/logout-success")
    public String getLogoutPage(Model model){
        return "logout";
    }

    @GetMapping(value="/students")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getStudents(Model model){
        List<Student> students = this.studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students-view";
    }

    @GetMapping(value="/students/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddStudentForm(Model model){
        return "student-view";
    }

    @PostMapping(value="/students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addStudent(HttpServletRequest request, Model model, @ModelAttribute StudentModel studentModel){
        Student student = this.studentService.addStudent(studentModel);
        model.addAttribute("student", student);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/students/" + student.getId());
    }

    @GetMapping(value="/students/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getStudent(Model model, @PathVariable long id){
        Student student = this.studentService.getStudent(id);
        model.addAttribute("student", student);
        return "student-view";
    }

    @PostMapping(value="/students/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateStudent(Model model, @PathVariable long id, @ModelAttribute StudentModel studentModel){
        Student student = this.studentService.updateStudent(id, studentModel);
        model.addAttribute("student", student);
        model.addAttribute("studentModel", new StudentModel());
        return "student-view";
    }
}
