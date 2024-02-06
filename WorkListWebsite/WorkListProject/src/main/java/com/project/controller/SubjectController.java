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

import com.project.Repository.SubjectDetailRepository;
import com.project.Repository.SubjectRepository;
import com.project.Service.WorkService;
import com.project.table.Subject;
import com.project.table.SubjectDetail;

@Controller
public class SubjectController implements ControllerFactory<Subject> {
	@Autowired
	private SubjectDetailRepository subjectDetailRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	private WorkService workService;

	@Override
	@Autowired
	public void setWorkService(WorkService w) {
		this.workService = w;
	}

//	@RequestMapping("/list_subject")
//    public String getSubjectList(Model model) {
//        System.out.println("Test in subjectList");
//        List<Subject> subjectList = workService.getSubjectAll();
//        // List<Category> categoryList = inventoryService.getCategoryById(null);
//        model.addAttribute("subjectList", subjectList);
//        return "subjectList";
//    }

	@RequestMapping("/list_subject")
	@Override
	public String list(@RequestParam(name = "query", required = false) String query, Model model) {
		List<Subject> subjectList;
		if (query != null && !query.isEmpty()) {
			// Perform a search based on the query
			List<Subject> subjectResults = workService.searchSubject(query);
			model.addAttribute("subjectResults", subjectResults);
		} else {
			// Load all works if no query is provided
			subjectList = workService.getSubjectAll();
			model.addAttribute("subjectList", subjectList);
		}

		return "subjectList"; // Return the template where you want to display the search results
	}

	@GetMapping("/delete_subject/{id}")
	@Override
	public String delete(@PathVariable("id") Integer id, Model model) {
		Subject subject = workService.getSubjectById(id);
		// .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
		// id));
		workService.deleteSubject(subject);
		return "redirect:/list_subject";
	}


	@GetMapping("/edit_subject/{id}")
	@Override
	public String edit(@PathVariable("id") Integer id, Model model) {
		try {
			Subject subject = workService.getSubjectById(id);

			model.addAttribute("subject", subject);

			// .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
			// id));
		} catch (Exception e) {
			System.err.printf(e.getMessage());

		}
		return "update_subject";
	}

	@GetMapping("/new_subject")
	@Override
	public String add(Model model) {
		SubjectDetail detail = new SubjectDetail();
		Subject subject = new Subject();
		subject.setSubjectDetail(detail);
		// customer.setAddress(address);
		model.addAttribute("subject", subject);

		return "new_subject";
	}
	
	@PostMapping("/update_subject/{id}")
	@Override
	public String update(@PathVariable("id") Integer id, @Validated Subject subject, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			subject.setIdsubject(id);
			return "update_subject";
		}

		// Retrieve the existing subject by ID
		Subject existingSubject = workService.getSubjectById(id);

		// Update properties of the existing subject
		existingSubject.setName(subject.getName());

		// Update properties of the associated SubjectDetail
		SubjectDetail subjectDetail = existingSubject.getSubjectDetail();
		subjectDetail.setDetail(subject.getDetail());
		subjectDetail.setMidtermScore(subject.getMid());
		subjectDetail.setFinalScore(subject.getFinal());
		subjectDetail.setProjectScore(subject.getProject());
		subjectDetail.setAssignmentScore(subject.getAssignment());

		// Save the existing subject, which will cascade to the associated SubjectDetail
		workService.saveSubject(existingSubject);

		return "redirect:/list_subject";
	}
	
	@PostMapping("/new_subject")
	@Override
	public String save(@Validated Subject subject, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "new_subject";
		}

		SubjectDetail detail = new SubjectDetail();
		// Set properties of the SubjectDetail here

		subject.setSubjectDetail(detail);
		detail.setSubject(subject);

		// Save the SubjectDetail first
		subjectDetailRepository.save(detail);

		// Then save the Subject
		subjectRepository.save(subject);

		return "redirect:/list_subject";
	}

	@RequestMapping("/subject_list_json")
	@ResponseBody
	@Override
	public List<Subject> json() {
		List<Subject> subjects = workService.getSubjectAll();
		return subjects;
	}
}