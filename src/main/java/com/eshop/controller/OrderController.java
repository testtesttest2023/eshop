package com.eshop.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eshop.dao.OrderDAO;
import com.eshop.dao.ProductDAO;
import com.eshop.entity.Customer;
import com.eshop.entity.Order;
import com.eshop.entity.Product;
import com.eshop.service.HttpService;
import com.eshop.service.ShoppingCartService;

@Controller
public class OrderController {
	@Autowired
	HttpService http;
	
	@Autowired
	OrderDAO odao;
	
	@Autowired
	ProductDAO pdao;
	
	@Autowired
	ShoppingCartService cart;
	
	@GetMapping("/order/checkout")
	public String checkout(Model model) {
		Order order = new Order();
		order.setOrderDate(new Date());
		order.setAmount(cart.getAmount());
		order.setCustomer(http.getSession("user"));
		
		model.addAttribute("order", order);
		model.addAttribute("cart", cart);
		return "order/checkout";
	}
	@PostMapping("/order/checkout")
	public String checkout(Model model, 
			@Validated @ModelAttribute("order") Order order, BindingResult errors) {
		order.setOrderDate(new Date());
		order.setAmount(cart.getAmount());
		order.setCustomer(http.getSession("user"));
		
		if(errors.hasErrors()) {
			model.addAttribute("message", "Please fix the bugs bellow");
			model.addAttribute("cart", cart);
			return "order/checkout";
		}
		odao.create(order, cart);
		cart.clear();
		model.addAttribute("message", "Purchase order successfully!");
		return "redirect:/order/list";
	}
	
	@RequestMapping("/order/list")
	public String list(Model model) {
		Customer user = http.getSession("user");
		List<Order> list = odao.findByUser(user);
		model.addAttribute("orders", list);
		return "order/list";
	}
	
	@RequestMapping("/order/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Order order = odao.findById(id);
		model.addAttribute("order", order);
		return "order/detail";
	}
	
	@RequestMapping("/order/items")
	public String getPurchasedItems(Model model) {
		Customer user = http.getSession("user");
		List<Order> list = odao.findByUser(user);
		
		Map<Integer, Product> prods = new HashMap<>();
		list.forEach(order -> {
			order.getOrderDetails().forEach(detail -> {
				Product p = detail.getProduct();
				prods.put(p.getId(), p);
			});
		});
		model.addAttribute("prods", prods.values());
		return "product/list";
	}
}
