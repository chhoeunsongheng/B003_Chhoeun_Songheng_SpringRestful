package com.hrd.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hrd.spring.service.impl.UserServiceImpl;

@Controller
public class webController {
	private UserServiceImpl userService;
	
	
	@Autowired         
	public webController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@RequestMapping("/dashboard")
	public String homePage(){
		return "/admin/dashboard";
	}
	
	@RequestMapping("/user_list")
	public String userlist(ModelMap model){
		model.addAttribute("user", userService.findAll());
		return "/admin/user_list";
	}

}
