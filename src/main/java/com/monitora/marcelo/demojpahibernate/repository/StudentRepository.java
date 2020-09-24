package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.entity.Course;
import com.monitora.marcelo.demojpahibernate.entity.Passport;
import com.monitora.marcelo.demojpahibernate.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class StudentRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    //create or update
    public Student save(Student student) {
        if (student.getId() == null) {
            entityManager.persist(student);
        } else {
            entityManager.merge(student);
        }
        entityManager.flush();
        return student;
    }

    public Student saveStudentWithPassport() {
        Student mike = new Student("Mike");
        Passport passport = new Passport("M654321");
        entityManager.persist(passport);
        mike.setPassport(passport);
        entityManager.persist(mike);
        return mike;
    }

    public void deleteById(Long id) {
        Student student = findById(id);
        entityManager.remove(student);
    }

    public Student retrieveCourseByStudent(long id) {
//        id = 20002L;
        Student student = entityManager.find(Student.class, id);
        return student;
//        logger.info("Student --> {}", student);
//        logger.info("Course from student --> {}", student.getCourses());
    }

//    public void playWithEntityManager() {
//        logger.info("playWithEntityManager --> started");
//
//        Course course = new Course("Padawan Front-end 2.0 - ReactNative");
//        entityManager.persist(course);
//        entityManager.flush();
////        entityManager.clear(); // comando para limpar a transação, pois o EntityManager mantém a transação até o fim da função e caso utilize o objeto e faça alguma alteração nele o mesmo é refletido no banco
////        entityManager.detach(course); // O estado Detached significa que o objeto não está vinculado ao EntityManager. Qualquer alteração não irá impactar no dado persistido. Isso só irá ocorrer se o estado voltar a ser Managed
//
//        Course course2 = new Course("Padawan Back-end 2.0 - Java");
//        entityManager.persist(course2);
//        entityManager.flush();
//
//
//        course.setName("Padawan Front-end 2.0 - ReactNative - alterado");
//        course2.setName("Padawan Back-end 2.0 - Java - alterado");
//
//        entityManager.refresh(course);
//        entityManager.flush();
//
//        Course course3 = findById(10001L);
//        course3.setName("JPA curso udemy - Indiano");
//        save(course3);
//
//    }

    public void insertHardCodedStudentAndCourse() {
        Student student = new Student("Marcelo");
        Course course = new Course("JPA in 28 minutes");
        entityManager.persist(student);
        entityManager.persist(course);

        student.addCourses(course);
        course.addStudents(student);
        entityManager.persist(student);
    }

    public void insertStudentAndCourse(Student student, Course course ) {
//        Student student = new Student("Marcelo");
//        Course course = new Course("JPA in 28 minutes");
        entityManager.persist(student);
        entityManager.persist(course);

        student.addCourses(course);
        course.addStudents(student);
    }


}
