package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "courses")
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {

    //customizando métodos do JpaRepository
    List<Course> findByName(String name);

    List<Course> findByNameAndId(String name, Long id);

    List<Course> countByName(String name);

    List<Course> deleteByName(String name);

    @Query("Select c From Course c where name like '%2.0'")
    List<Course> courseWithLike();

    @Query(value = "Select * from Course c where name like '%2.0'", nativeQuery = true)
    List<Course> courseWithLikeNativeQuery();

    @Query(name = "query_get_all_course")
    List<Course> courseWithLikeNamedQuery();
}
