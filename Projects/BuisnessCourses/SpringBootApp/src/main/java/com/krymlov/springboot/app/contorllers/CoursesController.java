package com.krymlov.springboot.app.contorllers;

import com.krymlov.springboot.app.models.Course;
import com.krymlov.springboot.app.repo.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CoursesController {

    @Autowired
    private ICourseRepository courseRepository;

    @GetMapping("/courses")
    public String courses(Model model){
        model.addAttribute("title", "Here is the list of active courses:");
        Iterable<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/courses/add")
    public String coursesAdd(Model model){
        model.addAttribute("title", "Add new course:");
        return "courses-add";
    }

    @PostMapping("/courses/add")
    public String coursesAddCourse(@RequestParam String title, @RequestParam String description, @RequestParam String price, Model model){
        Course course = new Course(title, description, price);
        courseRepository.save(course);
        return "redirect:/courses";
    }
}
