package kr.ac.ks.app.controller;

import kr.ac.ks.app.domain.Lesson;
import kr.ac.ks.app.repository.LessonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LessonController {

    private final LessonRepository lessonRepository;

    public LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
    @GetMapping(value = "/lessons/new")
    public String createForm(Model model) {
        model.addAttribute("lessonForm", new LessonForm());
        return "lessons/lessonForm";
    }
    @PostMapping(value = "/lessons/new")
    public String create(LessonForm form) {
        Lesson lesson = new Lesson();
        lesson.setName(form.getName());
        lesson.setQuota(form.getQuota());
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }
    @GetMapping(value = "/lessons")
    public String list(Model model) {
        List<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("lessons", lessons);
        return "lessons/lessonList";
    }
    @GetMapping("/lessons/update/{id}")
    public String getLessonUpdate(@PathVariable("id") Long id, Model model) {
        Lesson lesson = lessonRepository.findById(id).get();
        LessonForm lessonForm = new LessonForm();
        lessonForm.setName(lesson.getName());
        lessonForm.setQuota(lesson.getQuota());
        model.addAttribute("lessonForm", lessonForm);
        model.addAttribute("lessonId", id);
        return "/lessons/lessonUpdate";
    }
    @PostMapping("/lessons/update/{id}")
    public String postLessonUpdate(@PathVariable("id") Long id, LessonForm lessonForm) {
        Lesson lesson = lessonRepository.findById(id).get();
        lesson.setName(lessonForm.getName());
        lesson.setQuota(lessonForm.getQuota());
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }
    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable("id") Long id) {
        Lesson lesson = lessonRepository.findById(id).get();
        lessonRepository.delete(lesson);
        return "redirect:/lessons";
    }
}
