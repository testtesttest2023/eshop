package com.eshop.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eshop.dao.CategoryDAO;
import com.eshop.dao.ReportDAO;
import com.eshop.entity.Category;

@Controller
public class ReportAdminController {
	@Autowired
	ReportDAO dao;
	@Autowired
	CategoryDAO cdao;
	
	@RequestMapping("/admin/inventory/category")
	public String inventoryByCategory(Model model) {
		model.addAttribute("items", dao.getInventoryByCategory());
		return "admin/report/inventory-by-category";
	}
	
	@RequestMapping("/admin/revenue/product")
	public String revenueByProduct(Model model, 
			@RequestParam(name="category_id", required = false) Integer category_id) {
		List<Category> list = cdao.findAll();
		model.addAttribute("categories", list);
		if(category_id == null) {
			category_id = list.get(0).getId();
		}
		model.addAttribute("items", dao.getRevenueByProduct(category_id));
		return "admin/report/revenue-by-product";
	}
	
	@RequestMapping("/admin/revenue/category")
	public String revenueByCategory(Model model) {
		model.addAttribute("items", dao.getRevenueByCategory());
		return "admin/report/revenue-by-category";
	}
	
	@RequestMapping("/admin/revenue/customer")
	public String revenueByCustomer(Model model) {
		model.addAttribute("items", dao.getRevenueByCustomer());
		return "admin/report/revenue-by-customer";
	}
	
	@RequestMapping("/admin/revenue/year")
	public String revenueByYear(Model model) {
		model.addAttribute("items", dao.getRevenueByYear());
		return "admin/report/revenue-by-year";
	}
	
	@RequestMapping("/admin/revenue/quarter")
	public String revenueByQuarter(Model model) {
		model.addAttribute("items", dao.getRevenueByQuarter());
		return "admin/report/revenue-by-quarter";
	}
	
	@RequestMapping("/admin/revenue/month")
	public String revenueByMonth(Model model) {
		model.addAttribute("items", dao.getRevenueByMonth());
		return "admin/report/revenue-by-month";
	}
}
