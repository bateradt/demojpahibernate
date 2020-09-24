package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.entity.Course;
import com.monitora.marcelo.demojpahibernate.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class ReviewRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    public Review findById(Long id) {
        return entityManager.find(Review.class, id);
    }

    //create or update
    public Review save(Review review) {
        if (review.getId() == null) {
            entityManager.persist(review);
        } else {
            entityManager.merge(review);
        }
        entityManager.flush();
        return review;
    }

}
