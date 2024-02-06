package com.project.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.project.table.Work;

public interface WorkRepository extends CrudRepository<Work, Integer>{

	List<Work> findByNameContaining(String query);


}
