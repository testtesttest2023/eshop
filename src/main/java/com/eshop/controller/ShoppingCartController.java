package com.eshop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eshop.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
	@Autowired
	ShoppingCartService cart;
	
	@RequestMapping("/cart/aside/info")
	public String info(Model model) {
		model.addAttribute("cart", cart);
		return "no-layout/layout/aside-cart";
	}
	
	@RequestMapping("/cart/view")
	public String view(Model model) {
		model.addAttribute("cart", cart);
		return "cart/view";
	}
	
	@ResponseBody
	@RequestMapping("/cart/add/{id}")
	public Map<String, Object> add(@PathVariable("id") Integer id) {
		cart.add(id);
		return getJsonInfo();
	}
	
	@ResponseBody
	@RequestMapping("/cart/remove/{id}")
	public Map<String, Object> remove(@PathVariable("id") Integer id) {
		cart.remove(id);
		return getJsonInfo();
	}
	
	@ResponseBody
	@RequestMapping("/cart/update/{id}/{qty}")
	public Map<String, Object> update(@PathVariable("id") Integer id, @PathVariable("qty") Integer qty) {
		cart.update(id, qty);
		return getJsonInfo();
	}
	
	@ResponseBody
	@RequestMapping("/cart/clear")
	public Map<String, Object> clear() {
		cart.clear();
		return getJsonInfo();
	}

	private Map<String, Object> getJsonInfo() {
		Map<String, Object> map = new HashMap<>();
		map.put("count", cart.getCount());
		map.put("amount", String.format("%.2f", cart.getAmount()));
		return map;
	}
}
