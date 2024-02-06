package com.project.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.Service.WorkService;
import com.project.table.Status;
import com.project.table.Subject;
import com.project.table.Type;
import com.project.table.Work;

import java.lang.String;

@Controller // This means that this class is a Controller
public class WorkController implements ControllerFactory<Work> {

	private WorkService workService;

	@Autowired
	public void setWorkService(WorkService w) {
		this.workService = w;
	}

	@GetMapping("/")
	public String index(@RequestParam(name = "query", required = false) String query, Model model) {
		List<Work> workList;
		if (query != null && !query.isEmpty()) {
			// Perform a search based on the query
			List<Work> workResults = workService.searchWork(query);
			List<Status> statusResults = workService.searchStatus(query);
			model.addAttribute("workResults", workResults);
			model.addAttribute("statusResults", statusResults);
		} else {
			// Load all works if no query is provided
			workList = workService.getWorktAll();
			model.addAttribute("workList", workList);
		}

		return "worktList"; // Return the template where you want to display the search results
	}

//  @RequestMapping("/list_work")
//  public String workForm(Model model) {
//   
//	  List<Work> work = (List<Work>)workService.getWorktAll();
//	  model.addAttribute("work",work);
//    return "worktList";
//  }

	@GetMapping("/list_work")
	@Override
	public String list(@RequestParam(name = "query", required = false) String query, Model model) {
		List<Work> workList;
		if (query != null && !query.isEmpty()) {
			// Perform a search based on the query
			List<Work> workResults = workService.searchWork(query);
			List<Status> statusResults = workService.searchStatus(query);
			model.addAttribute("workResults", workResults);
			model.addAttribute("statusResults", statusResults);
		} else {
			// Load all works if no query is provided
			workList = workService.getWorktAll();
			model.addAttribute("workList", workList);
		}

		return "worktList"; // Return the template where you want to display the search results
	}

	@GetMapping("/delete_work/{id}")
	@Override
	public String delete(@PathVariable("id") Integer id, Model model) {
		// Product product = productRepository.findById((int) id)
		// .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
		// productRepository.delete(product);
		Work work = workService.getWorkById(id);
		workService.deleteWork(work);
		return "redirect:/list_work";
	}

	@PostMapping("/update_work/{id}")
	@Override
	public String update(@PathVariable("id") Integer id, @Validated Work work, BindingResult result, Model model) {
	    Work existingWork = workService.getWorkById(id);

	    if (existingWork == null) {
	        // Handle the case when the Work with the specified ID is not found
	        return "error"; // Create an error page
	    }

	    if (result.hasErrors()) {
	        work.setIdwork(id);
	        // You can add validation messages to the model
	        model.addAttribute("validationErrors", result.getAllErrors());
	        return "update_work";
	    }

	    // Update the attributes of the existing Work object
	    existingWork.setName(work.getName());
	    existingWork.setDate(work.getDate());

	    // Assuming 'status' is the ID of the selected status and 'type' is the ID of the selected type
	    int selectedStatusId = work.getStatus().getID();
	    int selectedTypeId = work.getType().getID();

	    // Retrieve the Status and Type objects from the database using their IDs
	    Status newStatus = workService.getStatusById(selectedStatusId);
	    Type newType = workService.getTypeById(selectedTypeId);

	    if (newStatus != null) {
	        existingWork.setStatus(newStatus);
	    }

	    if (newType != null) {
	        existingWork.setType(newType);
	    }

	    // Save the updated Work
	    workService.saveWork(existingWork);
	    return "redirect:/list_work";
	}


	@GetMapping("/edit_work/{id}")
	@Override
	public String edit(@PathVariable("id") Integer id, Model model) {
		Work work = workService.getWorkById(id);

        if (work == null) {
            // Handle the case when the Work with the specified ID is not found
            return "error"; // Create an error page
        }

        List<Work> workList = workService.getWorktAll();
        List<Subject> subjectList = workService.getSubjectAll();
        List<Type> typeList = workService.getTypeAll();
        List<Status> statusList = workService.getStatusAll();

        model.addAttribute("workList", workList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("typeList", typeList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("work", work);
		return "update_work";
	}

	@PostMapping("/new_work")
	@Override
	public String save(@Validated Work work, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<Work> workList = (List<Work>) workService.getWorktAll();
			model.addAttribute("workList", workList);
			return "new_workt";
		}

		workService.saveWork(work);
		return "redirect:/list_work";
	}

	@GetMapping("/new_work")
	@Override
	public String add(Model model) {
		Work work = new Work();
		model.addAttribute("work", work);

		List<Subject> subjectList = (List<Subject>) workService.getSubjectAll();
		model.addAttribute("subjectList", subjectList);

		List<Type> typeList = (List<Type>) workService.getTypeAll();
		model.addAttribute("typeList", typeList);

		List<Status> statusList = (List<Status>) workService.getStatusAll();
		model.addAttribute("statusList", statusList);

		return "new_work";
	}

	@RequestMapping("/work_list_json")
	@ResponseBody
	@Override
	public List<Work> json() {
		List<Work> works = workService.getWorktAll();
		return works;
	}

}