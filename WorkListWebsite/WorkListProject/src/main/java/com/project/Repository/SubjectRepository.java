package com.project.Repository;

import java.util.List;
import com.project.table.Subject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface SubjectRepository extends CrudRepository<Subject, Integer> {
	@Query("SELECT s FROM Subject s WHERE s.Name LIKE %:name%")
	List<Subject> findByNameContaining(@Param("name") String name);

}

