package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.DemojpahibernateApplication;
import com.monitora.marcelo.demojpahibernate.entity.Course;
import com.monitora.marcelo.demojpahibernate.entity.Review;
import com.monitora.marcelo.demojpahibernate.entity.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemojpahibernateApplication.class)
class CourseRepositoryTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;


	@Test
	public void testFindById_Repository() {
		Course course = courseRepository.findById(10001L);
		Assert.assertEquals("JPA curso udemy", course.getName());
	}

	@Test
	@DirtiesContext
	public void testDeleteById() {
		courseRepository.deleteById(10002L);
		Course course = courseRepository.findById(10002L);
		Assert.assertNull(course);
	}

	@Test
	@DirtiesContext
	public void testSaveNewCourse() {
		Course course = new Course("Padawan Front-end 2.0");
		Course courseSaved = courseRepository.save(course);

		Assert.assertNotNull(courseSaved.getId());
	}

	@Test
	@DirtiesContext
	public void testUpdateCourse() {
		Course course = courseRepository.findById(10001L);
		course.setName("JPA curso udemy - 28 minutes updated");
		Course courseSaved = courseRepository.save(course);

		Assert.assertEquals(courseSaved, course);
	}

	@Test
	@DirtiesContext
	public void playWithEntityManager() {
		courseRepository.playWithEntityManager();
	}

	@Test
	@Transactional
	public void retrieveReviewsForCourse() {
		Course course = courseRepository.findById(10001L);
		logger.info("Reviews from Course --> {}", course.getReviews());
	}


	@Test
	@Transactional
	public void retrieveCourseForReview() {
		Review review = courseRepository.find(Review.class, 50001L);
		logger.info("Course from review --> {}", review.getCourse());
	}

	@Test
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void testRetrieveStudentsByCourse() {
		Course course = courseRepository.retrieveStudentsByCourse(10002L);
		logger.info("Course --> {}", course);
		logger.info("Students from course --> {}", course.getStudents());
	}

}
