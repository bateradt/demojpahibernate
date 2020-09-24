package com.monitora.marcelo.demojpahibernate.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
//@Table(name="CourseDetails") // define um nome para a table, por padrão ao criar uma tabela o nome será trocado para course_details pois é um padrão
@Table(name="Course")

@NamedQueries(value = {
        @NamedQuery(name="query_get_all_course", query="Select c From Course c"),
        @NamedQuery(name="query_get_all_course_join_fetch", query="Select c From Course c JOIN FETCH c.students s"),
        @NamedQuery(name="query_get_Like2.0_course", query="Select c From Course c where name like '%2.0'")
})
@Cacheable
@SQLDelete(sql="update course set is_deleted=true where id=?")
@Where(clause = "is_deleted = false")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

//    @Column(name="fullname", nullable = false)
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "course") //padrão do Fetch é Lazy
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    private boolean isDeleted;

    @PreRemove
    private void preRemove() {
        this.isDeleted = true;
    }

    public Course() {

    }

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    // criamos os métodos para adicionar um review na lista e outro para remover o review da lista
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudents(Student students) {
        this.students.add(students);
    }

    public void removeStudents(Student students) {
        this.students.remove(students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(getId(), course.getId()) &&
                Objects.equals(getName(), course.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
