package com.monitora.marcelo.demojpahibernate;

import com.monitora.marcelo.demojpahibernate.entity.*;
import com.monitora.marcelo.demojpahibernate.repository.CourseRepository;
import com.monitora.marcelo.demojpahibernate.repository.EmployeeRepository;
import com.monitora.marcelo.demojpahibernate.repository.ReviewRepository;
import com.monitora.marcelo.demojpahibernate.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemojpahibernateApplication implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ReviewRepository reviewRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemojpahibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		studentRepository.saveStudentWithPassport();
//		courseRepository.addHardCodedReviewsForCourse();

		Review review1 = new Review(ReviewRating.FIVE, "Curso bom pra k7");
		Review review2 = new Review(ReviewRating.TWO, "Curso parecia que era ruim agora miorou");

		List<Review> reviews = new ArrayList<>();

		reviews.add(review1);
		reviews.add(review2);

		courseRepository.addReviewsForCourse(10002L, reviews);

		Review review3 = reviewRepository.findById(1L);
		review3.setRating(ReviewRating.FOUR);
		reviewRepository.save(review3);

		studentRepository.insertHardCodedStudentAndCourse();

		studentRepository.insertStudentAndCourse(new Student("José"), new Course("Micro Services with Node"));


//		courseRepository.playWithEntityManager();
//		Course course = courseRepository.findById(10001L);

//		logger.info("Course -> 10001L {} ", course);

//		delete method
//		courseRepository.deleteById(10001L);

//		insert method
//		courseRepository.save(new Course("React Native in 50 steps"));

//		update method
//		course.setName("JPA curso udemy - 28 minutes");
//		courseRepository.save(course);

		//inserindo registro na entity Employee
		employeeRepository.insert(new PartTimeEmployee("Maria", new BigDecimal("50")));

		employeeRepository.insert(new FullTimeEmployee("José", new BigDecimal("1000")));

//		logger.info("Employees --> {}", employeeRepository.retrieveAllEmployees());

		logger.info("PartTimeEmployees --> {}", employeeRepository.retrieveAllPartTimeEmployees());

		logger.info("FullTimeEmployees --> {}", employeeRepository.retrieveAllFullTimeEmployee());

	}
}
