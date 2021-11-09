package com.krymlov.springboot.app.repo;

import com.krymlov.springboot.app.models.Article;
import org.springframework.data.repository.CrudRepository;

public interface IArticleRepository extends CrudRepository<Article, Long> {

}
