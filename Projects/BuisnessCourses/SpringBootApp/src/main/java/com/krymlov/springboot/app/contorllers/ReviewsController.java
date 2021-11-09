package com.krymlov.springboot.app.contorllers;

import com.krymlov.springboot.app.models.Course;
import com.krymlov.springboot.app.models.Review;
import com.krymlov.springboot.app.repo.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewsController {

    @Autowired
    private IReviewRepository reviewRepository;

    @GetMapping("/reviews")
    public String reviews(Model model){
        model.addAttribute("title", "Here is list of reviews:");
        Iterable<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/reviews/add")
    public String reviewsAdd(Model model){
        model.addAttribute("title", "Add new course:");
        return "reviews-add";
    }

    @PostMapping("/reviews/add")
    public String reviewsAddReview(@RequestParam String author, @RequestParam String text, Model model){
        Review review = new Review(author, text);
        reviewRepository.save(review);
        return "redirect:/reviews";
    }
}
