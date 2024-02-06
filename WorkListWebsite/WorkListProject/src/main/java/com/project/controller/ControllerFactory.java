package com.project.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.project.Service.WorkService;

public interface ControllerFactory<Table> {
	void setWorkService(WorkService w);
	String list(String query, Model model);
	String delete(Integer id, Model model);
	
	//go to page edit
	String edit(Integer id, Model model);
	
	//update thing that you're edit
	String update(Integer id, Table table, BindingResult result, Model model);

	
	//go to page add
	String add(Model model);
	
	//save new that you add
	String save(Table table, BindingResult result, Model model);
	
	//json file
	List<Table> json();
	
	
	
	
}
