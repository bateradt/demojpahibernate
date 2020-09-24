package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.DemojpahibernateApplication;
import com.monitora.marcelo.demojpahibernate.entity.Course;
import com.monitora.marcelo.demojpahibernate.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemojpahibernateApplication.class)
class CriteriaQueriesTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	//"Select c From Course c"

	@Test
	public void criteria_query_example() {
		//1 -
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		//2 -
		Root<Course> courseRoot = cq.from(Course.class);


		//3 -

		//4 -

		//5 -
		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		logger.info("SELECT C FROM COURSE C -> {}", resultList);
	}

	@Test
	public void criteria_query_course_with_where() {
		//"Select c From Course c where name like '%JS%'"
		//1 -
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		//2 -
		Root<Course> courseRoot = cq.from(Course.class);

		//3 -
		Predicate likeJS = cb.like(courseRoot.get("name"), "%JS%");

		//4 -
		cq.where(likeJS);

		//5 -
		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		logger.info("SELECT C FROM COURSE C -> {}", resultList);
	}

	@Test
	public void criteria_query_all_course_without_students() {
		//"Select c From Course c where c.students is empty"
		//1 -
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		//2 -
		Root<Course> courseRoot = cq.from(Course.class);

		//3 -
		Predicate studentsEmpty = cb.isEmpty(courseRoot.get("students"));

		//4 -
		cq.where(studentsEmpty);

		//5 -
		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		logger.info("SELECT C FROM COURSE C -> {}", resultList);
	}

	@Test
	public void criteria_query_course_join_student() {
		//"Select c From Course c join c.students s"
		//1 -
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		//2 -
		Root<Course> courseRoot = cq.from(Course.class);

		//3 -
		Join<Object, Object> studentsJoin = courseRoot.join("students");

		//4 -


		//5 -
		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		logger.info("Join -> {}", resultList);
	}

	@Test
	public void criteria_query_course_left_join_student() {
		//"Select c From Course c join c.students s"
		//1 -
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);

		//2 -
		Root<Course> courseRoot = cq.from(Course.class);

		//3 -
		Join<Object, Object> studentsJoin = courseRoot.join("students", JoinType.LEFT);

		//4 -


		//5 -
		TypedQuery<Course> query = entityManager.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		logger.info("Left Join -> {}", resultList);
	}



}
