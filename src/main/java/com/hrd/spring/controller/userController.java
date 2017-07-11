package com.hrd.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hrd.spring.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/admin/user")
public class userController {
	private UserServiceImpl userService;
	@Autowired
	public userController(UserServiceImpl userService) {
		super();
		this.userService = userService;
	}
	@RequestMapping("/dashboard")
	public String Dashboard(ModelMap model){
		 	int totalUsers = userService.countTotalUsers();
	        int totalMale = userService.countMale();
	        int totalFemale = userService.countFemale();

	        model.addAttribute("TOTALUSER", totalUsers);
	        model.addAttribute("TOTALMALE", totalMale);
	        model.addAttribute("TOTALFEMALE", totalFemale);
		return "/admin/dashboard";
	}
	
	@RequestMapping
	public String user(){
		return "/admin/user_list";
	}
	
	@RequestMapping("/fragment/users")
	public String listUser(ModelMap model){
		model.addAttribute("USER", userService.findAll());
		return "/admin/user_list::userList";
	}
	
}
