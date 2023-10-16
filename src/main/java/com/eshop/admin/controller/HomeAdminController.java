package com.eshop.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eshop.service.HttpService;

@Controller
public class HomeAdminController {
	@Autowired
	HttpService http;
	
	@RequestMapping("/admin/home/index")
	public String index() {
		return "admin/home/index";
	}
	
	@RequestMapping("/admin/home/login")
	public String login() {
		http.setSession("secure-uri", "/admin/home/index");
		return "redirect:/account/login";
	}
	
	@RequestMapping("/admin/home/logoff")
	public String logoff() {
		return "redirect:/account/logoff";
	}
	
	@RequestMapping("/admin/home/change")
	public String change() {
		http.setSession("secure-uri", "/admin/home/index");
		return "redirect:/account/change";
	}
}
