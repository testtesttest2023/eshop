package com.eshop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eshop.entity.Customer;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	HttpSession session;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Customer user = (Customer) session.getAttribute("user");
		if(user == null) {
			session.setAttribute("secure-uri", request.getRequestURI());
			response.sendRedirect("/account/login?message=" + response.encodeRedirectURL("Please login before using this function!"));
			return false;
		}
		if(!user.isAdmin() && request.getRequestURI().startsWith("/admin/")) {
			session.setAttribute("secure-uri", request.getRequestURI());
			response.sendRedirect("/account/login?message=" + response.encodeRedirectURL("Unauthorized, access denied!"));
			return false;
		}
		return true;
	}
}
