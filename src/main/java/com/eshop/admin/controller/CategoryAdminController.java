package com.eshop.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eshop.dao.CategoryDAO;
import com.eshop.entity.Category;

@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {
	@Autowired
	CategoryDAO dao;
	
	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("list", dao.findAll());
		return "admin/category/index";
	}
	
	@RequestMapping("edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("category", dao.findById(id));
		model.addAttribute("list", dao.findAll());
		return "admin/category/index";
	}
	
	@RequestMapping("create")
	public String create(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("category") Category form, BindingResult errors) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs above");
			model.addAttribute("list", dao.findAll());
			return "admin/category/index";
		}
		dao.create(form);
		params.addAttribute("message", "Create successfully");
		return "redirect:/admin/category/index";
	}
	
	@RequestMapping("update")
	public String update(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("category") Category form, BindingResult errors) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs bellow");
			model.addAttribute("list", dao.findAll());
			return "admin/category/index";
		}
		dao.update(form);
		params.addAttribute("message", "Update successfully");
		return "redirect:/admin/category/edit/" + form.getId();
	}
	
	@RequestMapping("delete/{id}")
	public String delete(Model model, RedirectAttributes params,
			@PathVariable("id") Integer id) {
		dao.delete(id);
		params.addAttribute("message", "Delete successfully");
		return "redirect:/admin/category/index";
	}
}
