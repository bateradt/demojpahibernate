package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.DemojpahibernateApplication;
import com.monitora.marcelo.demojpahibernate.entity.Address;
import com.monitora.marcelo.demojpahibernate.entity.Course;
import com.monitora.marcelo.demojpahibernate.entity.Passport;
import com.monitora.marcelo.demojpahibernate.entity.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemojpahibernateApplication.class)
class StudentRepositoryTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	EntityManager entityManager;

	@Test
	@Transactional
	public void getStudentAndPassport() {
		Student student = entityManager.find(Student.class, 20001L);
		logger.info("Student -> {}", student);
		logger.info("Passport -> {}", student.getPassport());

//		Student student = courseRepository.findById(10001L);
//		Assert.assertEquals("JPA curso udemy", student.getName());
	}

	@Test
	@Transactional
	public void getStudentByPassport() {
		Passport passport = entityManager.find(Passport.class, 40001L);
		logger.info("Passport -> {}", passport);
		logger.info("Student -> {}", passport.getStudent());
	}

	@Test
	@Transactional
	public void someTest() {
		Student student = entityManager.find(Student.class, 20001L);
		logger.info("Student -> {}", student);
		logger.info("Passport -> {}", student.getPassport());

//		Student student = courseRepository.findById(10001L);
//		Assert.assertEquals("JPA curso udemy", student.getName());
	}

	@Test
	@Transactional
	public void testeRetrieveStudentWithCourses() {
		Student student = studentRepository.retrieveCourseByStudent(20002L);
		logger.info("Student --> {}", student);
        logger.info("Course from student --> {}", student.getCourses());
	}

	@Test
	@Transactional
	public void setAddressDetails() {
		Student student = entityManager.find(Student.class, 20001L);
		student.setAddress(new Address("Rua 1", "Vila 1", "Dois Corgo"));
		entityManager.flush();
		logger.info("Student -> {}", student);
//		logger.info("Passport -> {}", student.getPassport());
	}

}
