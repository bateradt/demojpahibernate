package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.DemojpahibernateApplication;
import com.monitora.marcelo.demojpahibernate.entity.Course;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemojpahibernateApplication.class)
class JPQLQueriesTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	@Test
	public void testFindById_Repository() {
		//o nome é da entidade e não da tabela no banco, ou seja, é a entnidade que está
		List result = entityManager.createQuery("SELECT C FROM Course C ").getResultList();
		logger.info("SELECT C FROM COURSE C -> {}", result);
	}

	@Test
	public void jql_type() {
		TypedQuery<Course> query = entityManager.createQuery("Select c From Course c", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("SELECT C FROM COURSE C -> {}", resultList);
	}

	@Test
	public void jql_where() {
		TypedQuery<Course> query = entityManager.createQuery("Select c From Course c where name like '%2.0'", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c where name like -> {}", resultList);
	}

	@Test
	public void test_named_query_course() {
		Query query = entityManager.createNamedQuery("query_get_all_course");
		List result = query.getResultList();
		logger.info("NameQuery query_get_all_course -> {}", result);
	}

	@Test
	public void test_named_query_course_with_like() {
		Query query = entityManager.createNamedQuery("query_get_Like2.0_course");
		List result = query.getResultList();
		logger.info("NameQuery query_get_Like2.0_course -> {}", result);
	}

	@Test
	public void test_jpql_courses_without_students() {

		TypedQuery<Course> query = entityManager.createQuery("Select c From Course c where c.students is empty", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Course without students -> {}", resultList);
	}

	@Test
	public void test_jpql_courses_atleast_2_students() {

		TypedQuery<Course> query = entityManager.createQuery("Select c From Course c where size(c.students) >= 2", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Course at least 2 students -> {}", resultList);
	}

	@Test
	public void test_jpql_courses_ordered_by_students() {

		TypedQuery<Course> query = entityManager.createQuery("Select c From Course c order by size(c.students) desc", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Course order by number of students -> {}", resultList);
	}

	// operador like
	@Test
	public void test_jpql_search_students_with_like_passport() {

		TypedQuery<Student> query = entityManager.createQuery("Select s From Student s where s.passport.number like '%1234%'", Student.class);
		List<Student> resultList = query.getResultList();
		logger.info("Students with document like -> {}", resultList);
	}

	//Course at least 2 students -> [Course{id=10004, name='PHP with laravel'}, Course{id=2, name='JPA in 28 minutes'}, Course{id=4, name='Micro Services with Node'}, Course{id=10001, name='JPA curso udemy'}, Course{id=10002, name='Livro de Ouro da Liderança'}, Course{id=10003, name='Bootcamp Rocketseat 12.0'}]

	@Test
	public void test_join() {
		Query query = entityManager.createQuery("Select c, s from Course c JOIN c.students s");
		//irá executar um joint da entidade Course com a Student
		List<Object[]> resultList = query.getResultList(); //retorna um array de objetos
		logger.info("JOIN Result size -> {}", resultList.size()); //tamanho do array
		for (Object[] result:resultList) { //faz um for do resultado do array
			logger.info("Course {} Student {}", result[0], result[1]);
		}
	}

	@Test
	public void test_left_join() {
		Query query = entityManager.createQuery("Select c, s from Course c LEFT JOIN c.students s");
		//irá executar um joint da entidade Course com a Student
		List<Object[]> resultList = query.getResultList(); //retorna um array de objetos
		logger.info("LEFT JOIN Result size -> {}", resultList.size()); //tamanho do array
		for (Object[] result:resultList) { //faz um for do resultado do array
			logger.info("Course {} Student {}", result[0], result[1]);
		}
	}

	@Test
	public void test_cross_join() {
		Query query = entityManager.createQuery("Select c, s from Course c, Student s");
		//irá executar um joint da entidade Course com a Student
		List<Object[]> resultList = query.getResultList(); //retorna um array de objetos
		logger.info("LEFT JOIN Result size -> {}", resultList.size()); //tamanho do array
		for (Object[] result:resultList) { //faz um for do resultado do array
			logger.info("Course {} Student {}", result[0], result[1]);
		}
	}



}
