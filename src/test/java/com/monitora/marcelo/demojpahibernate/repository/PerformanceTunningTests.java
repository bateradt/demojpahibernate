package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.DemojpahibernateApplication;
import com.monitora.marcelo.demojpahibernate.entity.Course;
import com.monitora.marcelo.demojpahibernate.entity.Review;
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

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemojpahibernateApplication.class)
class PerformanceTunningTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EntityManager entityManager;

	//aqui ele irá executar um SQL a mais para cada student do curso
	@Test
	@Transactional
	public void creatingNPlusOneProblem() {
		List<Course> courses = entityManager.createNamedQuery("query_get_all_course", Course.class).getResultList();
		for (Course course:courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}

	//aqui irá trazer tudo em um SQL
	@Test
	@Transactional
	public void solvingNPlusOneProblem_WithGraph() {
		EntityGraph<Course> entityGraph = entityManager.createEntityGraph(Course.class);
		Subgraph<Object> subGraph = entityGraph.addSubgraph("students");

		List<Course> courses = entityManager.createNamedQuery("query_get_all_course", Course.class)
				.setHint("javax.persistence.loadgraph", entityGraph)
				.getResultList();
		for (Course course:courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}

	@Test
	@Transactional
	public void solvingNPlusOneProblem_WithJoinFetch() {
		List<Course> courses = entityManager.createNamedQuery("query_get_all_course_join_fetch", Course.class).getResultList();
		for (Course course:courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}


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
