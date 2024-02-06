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
import com.project.table.Type;

@Controller
public class TypeController implements ControllerFactory<Type> {
    private WorkService workService;

    @Autowired
    @Override
    public void setWorkService(WorkService w) {
        this.workService = w;
    }

////    @RequestMapping("/list_type")
////    public String getCategoryList(Model model) {
////        System.out.println("Test in categoryList");
////        List<Type> typeList = workService.getTypeAll();
////        // List<Category> categoryList = inventoryService.getCategoryById(null);
////        model.addAttribute("typeList", typeList);
////        return "typeList";
////    }
//    
    @GetMapping("/list_type")
    @Override
    public String list(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Type> typeList;
        if (query != null && !query.isEmpty()) {
            // Perform a search based on the query
            List<Type> typeResults = workService.searchType(query);
            model.addAttribute("typeResults", typeResults);
        } else {
            // Load all works if no query is provided
        	typeList = workService.getTypeAll();
            model.addAttribute("typeList", typeList);
        }

        return "typeList"; // Return the template where you want to display the search results
    }

    @GetMapping("/delete_type/{id}")
    @Override
    public String delete(@PathVariable("id") Integer id, Model model) {
    	Type type = workService.getTypeById(id);
        // .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
        // id));
    	workService.deleteType(type);
        return "redirect:/list_type";
    }

    @PostMapping("/update_type/{id}")
    @Override
    public String update(@PathVariable("id") Integer id, @Validated Type type,
            BindingResult result, Model model) {
        System.out.println(id);
        System.out.println(type.getID() + type.getName());
        if (result.hasErrors()) {
            type.setID(id);
            return "update_type";
        }

        Type existingType = workService.getTypeById(id);

        if (existingType != null) {
            // Update the attributes of the existing status object
        	existingType.setName(type.getName());
            
            // Save the updated status
            workService.saveType(existingType);
        }
        return "redirect:/list_type";
    }

    @GetMapping("/edit_type/{id}")
    @Override
    public String edit(@PathVariable("id") Integer id, Model model) {
        try {
            Type type = workService.getTypeById(id);

            model.addAttribute("type", type);

            // .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" +
            // id));
        } catch (Exception e) {
            System.err.printf(e.getMessage());

        }
        return "update_type";
    }

    @PostMapping("/new_type")
    @Override
    public String save(@Validated Type type, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "new_type";
        }

        workService.saveType(type);
        return "redirect:/list_type";
    }

    @GetMapping("/new_type")
    @Override
    public String add(Model model) {
        Type type = new Type();
        model.addAttribute("type", type);

        return "new_type";
    }

    @RequestMapping("/type_list_json")
    @ResponseBody
    @Override
    public List<Type> json() {
        List<Type> types = workService.getTypeAll();
        return types;
    }
}

