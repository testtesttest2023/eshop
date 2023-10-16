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

import com.eshop.dao.OrderDAO;
import com.eshop.entity.Order;

@Controller
@RequestMapping("/admin/order")
public class OrderAdminController {
	@Autowired
	OrderDAO dao;
	
	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("order", new Order());
		model.addAttribute("list", dao.findAll());
		return "admin/order/index";
	}
	
	@RequestMapping("edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("order", dao.findById(id));
		model.addAttribute("list", dao.findAll());
		return "admin/order/index";
	}
	
	@RequestMapping("create")
	public String create(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("order") Order form, BindingResult errors) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs above");
			model.addAttribute("list", dao.findAll());
			return "admin/order/index";
		}
		dao.create(form);
		params.addAttribute("message", "Create successfully");
		return "redirect:/admin/order/index";
	}
	
	@RequestMapping("update")
	public String update(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("order") Order form, BindingResult errors) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs bellow");
			model.addAttribute("list", dao.findAll());
			return "admin/order/index";
		}
		dao.update(form);
		params.addAttribute("message", "Update successfully");
		return "redirect:/admin/order/edit/" + form.getId();
	}
	
	@RequestMapping("delete/{id}")
	public String delete(Model model, RedirectAttributes params,
			@PathVariable("id") Integer id) {
		dao.delete(id);
		params.addAttribute("message", "Delete successfully");
		return "redirect:/admin/order/index";
	}
}
