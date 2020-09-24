package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.DemojpahibernateApplication;
import com.monitora.marcelo.demojpahibernate.entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemojpahibernateApplication.class)
class CourseSpringDataRepositoryTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseSpringDataRepository courseRepository;


	@Test
	public void courseIsPresentInJPARepository() {
		Optional<Course> course = courseRepository.findById(10001L);
		assertTrue(course.isPresent());
	}

	@Test
	@Transactional
	public void course_FirstLevelCache() {
		Optional<Course> course = courseRepository.findById(10001L);
		assertTrue(course.isPresent());
		logger.info("First call -> {}", course);

		Optional<Course> course2 = courseRepository.findById(10001L);
		assertTrue(course.isPresent());
		logger.info("Second call -> {}", course2);
	}

	@Test
	public void courseIsNotPresentInJPARepository() {
		Optional<Course> course = courseRepository.findById(50001L);
		assertFalse(course.isPresent());
	}

	@Test
	public void playAroundWithJPARepository() {
		Course course = new Course("Curso starter JavaScript");
		courseRepository.save(course);
		logger.info("Courses --> {} ", courseRepository.findAll());

		course.setName("Curso starter JavaScript - updated");
		courseRepository.save(course);
		logger.info("Courses --> {} ", courseRepository.findAll());

		logger.info("Courses Count --> {} ", courseRepository.count());
	}

	@Test
	public void sort_Criteria() {
		Sort sort = Sort.by(Sort.Direction.DESC, "name");
		logger.info("Courses --> {} ", courseRepository.findAll(sort));

		logger.info("Courses Count --> {} ", courseRepository.count());
	}

	@Test
	public void pagination() {
		PageRequest pageRequest = PageRequest.of(0,3); //parametros da páginação, iniciando da 0 e 3 registros por páginas
		Page<Course> firstPage = courseRepository.findAll(pageRequest); //solicita os registros da página e grava em uma variavel a primeira página
		logger.info("Courses First Page --> {} ", firstPage.getContent());

		Pageable secondPageable = firstPage.nextPageable();
		Page<Course> secondPage = courseRepository.findAll(secondPageable);
		logger.info("Courses Second Page --> {} ", secondPage.getContent());
	}

	@Test
	public void deleteCourse() {
		courseRepository.deleteById(10001L);
//		logger.info("Course deleted --> {} ", course);
		assertTrue(courseRepository.findById(10001L).isEmpty());
	}



}
