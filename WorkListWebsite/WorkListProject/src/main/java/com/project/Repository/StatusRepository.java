package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.table.Status;

public interface StatusRepository extends CrudRepository<Status, Integer>{
	@Query("SELECT s FROM Status s WHERE s.Name LIKE %:name%")
	List<Status> findByNameContaining(@Param("name") String name);

}
