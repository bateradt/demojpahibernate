package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.entity.Course;
import com.monitora.marcelo.demojpahibernate.entity.Review;
import com.monitora.marcelo.demojpahibernate.entity.ReviewRating;
import com.monitora.marcelo.demojpahibernate.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class CourseRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    public <T> T find(Class<T> var1, Object var2) {
        return entityManager.find(var1, var2);
    }

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    //create or update
    public Course save(Course course) {
        if (course.getId() == null) {
            entityManager.persist(course);
        } else {
            entityManager.merge(course);
        }
        entityManager.flush();
        return course;
    }

    public void deleteById(Long id) {
        Course course = findById(id);
        entityManager.remove(course);
    }

    public void playWithEntityManager() {
        logger.info("playWithEntityManager --> started");

        Course course = new Course("Padawan Front-end 2.0 - ReactNative");
        entityManager.persist(course);
        entityManager.flush();
//        entityManager.clear(); // comando para limpar a transação, pois o EntityManager mantém a transação até o fim da função e caso utilize o objeto e faça alguma alteração nele o mesmo é refletido no banco
//        entityManager.detach(course); // O estado Detached significa que o objeto não está vinculado ao EntityManager. Qualquer alteração não irá impactar no dado persistido. Isso só irá ocorrer se o estado voltar a ser Managed

        Course course2 = new Course("Padawan Back-end 2.0 - Java");
        entityManager.persist(course2);
        entityManager.flush();


        course.setName("Padawan Front-end 2.0 - ReactNative - alterado");
        course2.setName("Padawan Back-end 2.0 - Java - alterado");

        entityManager.refresh(course);
        entityManager.flush();

        Course course3 = findById(10001L);
        course3.setName("JPA curso udemy - Indiano");
        save(course3);

    }

    public void addHardCodedReviewsForCourse() {
        Course course = findById(10003L);
        logger.info("course.getReviews() -> {}", course.getReviews());

        Review review1 = new Review(ReviewRating.FIVE, "Curso bom pra k7");
        Review review2 = new Review(ReviewRating.FOUR, "Curso parecia que era ruim agora miorou");

        course.addReview(review1);
        review1.setCourse(course);

        course.addReview(review2);
        review2.setCourse(course);

        entityManager.persist(review1);
        entityManager.persist(review2);
    }

    public void addReviewsForCourse(long courseId, List<Review> reviews) {
        Course course = findById(courseId);
        logger.info("course.getReviews() -> {}", course.getReviews());
        for (Review review:reviews) {
            course.addReview(review);
            review.setCourse(course);
            entityManager.persist(review);
        }
    }

    public Course retrieveStudentsByCourse(long id) {
        Course course = entityManager.find(Course.class, id);
        return course;
    }


}
