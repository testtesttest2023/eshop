package com.eshop.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eshop.dao.CategoryDAO;
import com.eshop.dao.ProductDAO;
import com.eshop.entity.Category;
import com.eshop.entity.Product;
import com.eshop.service.HttpService;
import com.eshop.service.MailerService;
@Controller
public class ProductController {
	@Autowired
	CategoryDAO cdao;
	@Autowired
	ProductDAO pdao;
	@Autowired
	HttpService http;
	@Autowired
	MailerService mailer;
	
	@RequestMapping("/product/list-by-category/{id}")
	public String listByCategory(Model model, @PathVariable("id") Integer id) {
		http.setSession("backurl", http.getCurrentUri());
		List<Product> list = cdao.findById(id).getProducts();
		model.addAttribute("prods", list);
		return "product/list";
	}
	
	@RequestMapping("/product/list-by-keywords")
	public String listByKeywords(Model model, @RequestParam("keywords") String keywords) {
		http.setSession("backurl", http.getCurrentUri());
		List<Product> list = pdao.findByKeywords(keywords);
		model.addAttribute("prods", list);
		return "product/list";
	}
	
	@RequestMapping("/product/list-by-hot/{hotkey}")
	public String listByHot(Model model, @PathVariable("hotkey") String hotkey) {
		http.setSession("backurl", http.getCurrentUri());
		List<Product> list = pdao.findByHot(hotkey);
		model.addAttribute("prods", list);
		return "product/list";
	}
	
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		http.setSession("backurl", http.getCurrentUri());
		Product product = pdao.findById(id);
		model.addAttribute("prod", product);
		
		String ids = http.getCookieValue("favos", "");
		if(!ids.isEmpty()) {
			List<Product> list = pdao.findByIds(ids);
			model.addAttribute("favos", list);			
		}
		
		return "product/detail";
	}

	@ResponseBody
	@RequestMapping("/product/favorite/{id}")
	public String[] favorite(@PathVariable("id") String id) {
		String ids = http.getCookieValue("favos", id);
		if(!ids.contains(id)) {
			ids += "," + id;
		}
		http.createCookie("favos", ids, 30);
		return ids.split(",");
	}
	
	@ResponseBody
	@PostMapping("/product/send")
	public boolean send(
			@RequestParam("id") String id,
			@RequestParam("sender") String from,
			@RequestParam("receiver") String to,
			@RequestParam("subject") String subject,
			@RequestParam("body") String body) {
		String url = http.getCurrentUrl().replace("send", "detail/"+id);
		body += "<hr/><a href='"+url+"'>Xem chi tiết</a>";
		return mailer.send(to, subject, body, from);
	}
	
	@RequestMapping("/product/random")
	public String random(Model model) {
		List<Product> list = pdao.findAll();
		Collections.shuffle(list);
		model.addAttribute("prods", list.subList(0, 6));
		return "no-layout/product/list";
	}
	
	@RequestMapping("/product/slideshow")
	public String slideshow(Model model) {
		List<Category> list = cdao.findAll();
		Collections.shuffle(list);
		
		List<Category> result = new ArrayList<Category>();
		for(Category cate : list) {
			List<Product> prods = cate.getProducts();
			if(prods.size() >= 4) {
				Collections.shuffle(prods);
				cate.setProducts(prods.subList(0, 4));
				result.add(cate);
			}
		}
		model.addAttribute("cate5", result.subList(0, 4));
		return "no-layout/product/slideshow";
	}
}
