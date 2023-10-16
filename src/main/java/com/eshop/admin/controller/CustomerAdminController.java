package com.eshop.admin.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eshop.dao.CustomerDAO;
import com.eshop.entity.Customer;
import com.eshop.service.HttpService;

@Controller
@RequestMapping("/admin/customer")
public class CustomerAdminController {
	@Autowired
	CustomerDAO dao;
	@Autowired
	HttpService http;
	
	int pageSize = 10;
	
	@RequestMapping("index")
	public String index(Model model, 
			@RequestParam(name="pageNo", defaultValue = "-1") Integer pageNo) {
		Integer pageCount = http.getSession("pageCount");
		if(pageCount == null) {
			http.setSession("pageCount", pageCount = dao.getUserPageCount(pageSize));
		}
		
		if(pageNo >= 0 && pageNo <= pageCount - 1) {
			http.setSession("pageNo", pageNo);
		}
		pageNo = getPageNo();
		
		model.addAttribute("customer", new Customer());
		model.addAttribute("users", dao.findUsers(pageNo, pageSize));
		model.addAttribute("admins", dao.findAdmins());
		return "admin/customer/index";
	}
	
	private int getPageNo() {
		Integer page = http.getSession("pageNo");
		return page == null ? 0 : page;
	}

	@RequestMapping("edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		model.addAttribute("customer", dao.findById(id));
		int pageNo = getPageNo();
		model.addAttribute("users", dao.findUsers(pageNo, pageSize));
		model.addAttribute("admins", dao.findAdmins());
		return "admin/customer/index";
	}
	
	@RequestMapping("create")
	public String create(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("customer") Customer form, BindingResult errors,
			@RequestParam("photo_file") MultipartFile file) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs bellow");
			model.addAttribute("list", dao.findAll());
			return "admin/customer/index";
		}
		File photo = http.saveCustomerPhoto(file);
		if(photo != null) {
			form.setPhoto(photo.getName());
		}
		else {
			form.setPhoto("user.png");
		}
		dao.create(form);
		params.addAttribute("message", "Create successfully");
		return "redirect:/admin/customer/index";
	}

	@RequestMapping("update")
	public String update(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("customer") Customer form, BindingResult errors,
			@RequestParam("photo_file") MultipartFile file) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs bellow");
			model.addAttribute("list", dao.findAll());
			return "admin/customer/index";
		}
		File photo = http.saveCustomerPhoto(file);
		if(photo != null) {
			form.setPhoto(photo.getName());
		}
		dao.update(form);
		params.addAttribute("message", "Update successfully");
		return "redirect:/admin/customer/edit/" + form.getId();
	}
	
	@RequestMapping("delete/{id}")
	public String delete(Model model, RedirectAttributes params,
			@PathVariable("id") String id) {
		dao.delete(id);
		params.addAttribute("message", "Delete successfully");
		return "redirect:/admin/customer/index";
	}
}
