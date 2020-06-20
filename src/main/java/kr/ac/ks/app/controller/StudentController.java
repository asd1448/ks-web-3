package kr.ac.ks.app.controller;

import kr.ac.ks.app.domain.Student;
import kr.ac.ks.app.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students/new")
    public String showStudentForm(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "students/studentForm";
    }

    @PostMapping("/students/new")
    public String createStudent(@Valid StudentForm studentForm, BindingResult result) {
        if (result.hasErrors()) {
            return "students/studentForm";
        }

        Student student = new Student();
        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students/studentList";
    }

    @GetMapping("/students/update/{id}")
    public String getStudentUpdate(@PathVariable("id") Long id, Model model) {
        Student student = studentRepository.findById(id).get();

        StudentForm studentForm = new StudentForm();
        studentForm.setName(student.getName());
        studentForm.setEmail(student.getEmail());
        model.addAttribute("studentForm", studentForm);
        model.addAttribute("studentId", id);
        return "students/studentUpdate";
    }
    @PostMapping("/students/update/{id}")
    public String postStudentUpdate(@PathVariable("id") Long id
            , @Valid StudentForm studentForm, BindingResult result) {

        if(result.hasErrors()) {
            return "students/studentUpdate";
        }

        Student student = studentRepository.findById(id).get();
        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        studentRepository.save(student);
        return "redirect:/students";
    }
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        Student student = studentRepository.findById(id).get();
        studentRepository.delete(student);
        return "redirect:/students";
    }
}