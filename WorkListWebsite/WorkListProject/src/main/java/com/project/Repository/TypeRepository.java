package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.table.Type;

public interface TypeRepository extends CrudRepository<Type, Integer>{

	@Query("SELECT t FROM Type t WHERE t.Name LIKE %:name%")
	List<Type> findByNameContaining(@Param("name") String name);


}
