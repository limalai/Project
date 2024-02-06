package com.project.controller;

import java.util.List;

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

@Controller
public class StatusController implements ControllerFactory<Status> {
	private WorkService workService;

	@Autowired
	@Override
	public void setWorkService(WorkService w) {
		// TODO Auto-generated method stub
		this.workService=w;
	}

	@GetMapping("/list_status")
	@Override
	public String list(@RequestParam(name = "query", required = false) String query, Model model) {
		List<Status> statusList;
        if (query != null && !query.isEmpty()) {
            // Perform a search based on the query
            List<Status> statusResults = workService.searchStatus(query);
            model.addAttribute("statusResults", statusResults);
        } else {
            // Load all works if no query is provided
        	statusList = workService.getStatusAll();
            model.addAttribute("statusList", statusList);
        }

        return "statusList";
	}

	@GetMapping("/delete_status/{id}")
	@Override
	public String delete(@PathVariable("id") Integer id, Model model) {
		Status status = workService.getStatusById(id);
		// .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
		// id));
		workService.deleteStatus(status);
		return "redirect:/list_status";
	}

	@GetMapping("/edit_status/{id}")
	@Override
	public String edit(@PathVariable("id") Integer id, Model model) {
		try {
			Status status = workService.getStatusById(id);

			model.addAttribute("status", status);

			// .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
			// id));
		} catch (Exception e) {
			System.err.printf(e.getMessage());

		}
		return "update_status";
	}

	@PostMapping("/update_status/{id}")
	@Override
	public String update(@PathVariable("id") Integer id, @Validated Status status, BindingResult result,
			Model model) {
		System.out.println(id);
		System.out.println(status.getID() + status.getName());
		if (result.hasErrors()) {
			status.setID(id);
			return "update_status";
		}

		Status existingStatus = workService.getStatusById(id);

		if (existingStatus != null) {
			// Update the attributes of the existing status object
			existingStatus.setName(status.getName());

			// Save the updated status
			workService.saveStatus(existingStatus);
		}

		return "redirect:/list_status";
	}

	@GetMapping("/new_status")
	@Override
	public String add(Model model) {
		Status status = new Status();
		model.addAttribute("status", status);

		return "new_status";
	}

	@PostMapping("/new_status")
	@Override
	public String save(@Validated Status status, BindingResult result, Model model) {
		if (result.hasErrors()) {

			return "new_status";
		}

		workService.saveStatus(status);
		return "redirect:/list_status";
	}

	@RequestMapping("/status_list_json")
	@ResponseBody
	@Override
	public List<Status> json() {
		List<Status> status = workService.getStatusAll();
		return status;
	}

}
