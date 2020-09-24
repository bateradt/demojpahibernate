package com.monitora.marcelo.demojpahibernate.repository;

import com.monitora.marcelo.demojpahibernate.DemojpahibernateApplication;
import com.monitora.marcelo.demojpahibernate.entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemojpahibernateApplication.class)
class NativeQueriesTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager entityManager;

	@Test
	public void native_queries_basic() {
		Query query = entityManager.createNativeQuery("SELECT * FROM COURSE", Course.class);
		List result = query.getResultList();
		logger.info("native_queries_basic -> {}", result);
	}

	@Test
	public void native_queries_basic_with_parameter() {
		Query query = entityManager.createNativeQuery("SELECT * FROM COURSE where id = ?", Course.class);
		query.setParameter(1, 10001L);
		List result = query.getResultList();
		logger.info("native_queries_basic -> {}", result);
	}

	@Test
	public void native_queries_basic_with_named_parameter() {
		Query query = entityManager.createNativeQuery("SELECT * FROM COURSE where id = :id", Course.class);
		query.setParameter("id", 10001L);
		List result = query.getResultList();
		logger.info("native_queries_basic -> {}", result);
	}

	@Test
	@Transactional
	public void native_queries_basic_to_updated() {
		Query query = entityManager.createNativeQuery("Update COURSE set updated_at=sysdate()", Course.class);
		int numberOfRowsUpdated = query.executeUpdate();
		logger.info("numberOfRowsUpdated -> {}", numberOfRowsUpdated);
	}




}
