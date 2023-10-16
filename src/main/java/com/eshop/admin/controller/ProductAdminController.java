package com.eshop.admin.controller;

import java.io.File;
import java.util.List;

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

import com.eshop.dao.CategoryDAO;
import com.eshop.dao.ProductDAO;
import com.eshop.entity.Category;
import com.eshop.entity.Product;
import com.eshop.service.HttpService;

@Controller
@RequestMapping("/admin/product")
public class ProductAdminController {
	@Autowired
	ProductDAO dao;
	@Autowired
	HttpService http;
	@Autowired
	CategoryDAO cdao;
	
	@RequestMapping("index")
	public String index(Model model, 
			@RequestParam(name="category_id", required = false) Integer cate_id) {
		if(cate_id != null) {
			http.setSession("category", cdao.findById(cate_id));
		}
		
		Product product = new Product();
		product.setCategory(this.getCurrentCategory());
		model.addAttribute("product", product);
		model.addAttribute("list", dao.findByCategory(this.getCurrentCategory()));
		return "admin/product/index";
	}
	
	@RequestMapping("edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("product", dao.findById(id));
		model.addAttribute("list", dao.findByCategory(this.getCurrentCategory()));
		return "admin/product/index";
	}
	
	@RequestMapping("create")
	public String create(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("product") Product form, BindingResult errors,
			@RequestParam("image_file") MultipartFile file) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs bellow");
			model.addAttribute("list", dao.findAll());
			return "admin/product/index";
		}
		File image = http.saveProductImage(file);
		if(image != null) {
			form.setImage(image.getName());
		}
		else {
			form.setImage("user.png");
		}
		dao.create(form);
		params.addAttribute("message", "Create successfully");
		return "redirect:/admin/product/index";
	}

	@RequestMapping("update")
	public String update(Model model, RedirectAttributes params,
			@Validated @ModelAttribute("product") Product form, BindingResult errors,
			@RequestParam("image_file") MultipartFile file) {
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs bellow");
			model.addAttribute("list", dao.findAll());
			return "admin/product/index";
		}
		File image = http.saveProductImage(file);
		if(image != null) {
			form.setImage(image.getName());
		}
		dao.update(form);
		params.addAttribute("message", "Update successfully");
		return "redirect:/admin/product/edit/" + form.getId();
	}
	
	@RequestMapping("delete/{id}")
	public String delete(Model model, RedirectAttributes params,
			@PathVariable("id") Integer id) {
		dao.delete(id);
		params.addAttribute("message", "Delete successfully");
		return "redirect:/admin/product/index";
	}
	
	@ModelAttribute("categories")
	public List<Category> getCategories(){
		return cdao.findAll();
	}
	
	public Category getCurrentCategory() {
		Category category = http.getSession("category");
		return category;
	}
}
