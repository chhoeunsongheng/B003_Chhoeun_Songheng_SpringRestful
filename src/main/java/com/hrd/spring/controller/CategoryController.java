package com.hrd.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hrd.spring.entities.Form.CategoryFrmPP;
import com.hrd.spring.service.impl.CategoryServiceImpl;


@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	private CategoryServiceImpl categoryService;
	
	@Autowired
	public CategoryController(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}
	@RequestMapping
	public String categoryPage(){
		return "/admin/category";
	}

	@RequestMapping("/fragment/categories")
	public String categoryfragement(ModelMap model){
		model.addAttribute("cate",categoryService.findAll());
		return "/admin/category::categoriesList";
	}
	
}
