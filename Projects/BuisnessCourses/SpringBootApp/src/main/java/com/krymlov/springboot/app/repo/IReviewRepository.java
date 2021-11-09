package com.krymlov.springboot.app.repo;

import com.krymlov.springboot.app.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface IReviewRepository extends CrudRepository<Review, Long> {
}
