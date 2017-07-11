package com.hrd.spring.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrd.spring.entities.Category;
import com.hrd.spring.entities.Form.CategoryFrmPP;
import com.hrd.spring.repositories.CategoryRepository;
import com.hrd.spring.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	private CategoryRepository categoryRepo;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	@Override
	public Category findByUUID(String uuid) {
		return categoryRepo.findByUUID(uuid);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public boolean update(CategoryFrmPP categoryFrmPP) {
		return categoryRepo.update(categoryFrmPP);
	}

	@Override
	public boolean delete(String uuid) {
		return categoryRepo.delete(uuid);
	}

	@Override
	public boolean updateStatusByUUID(String status, String uuid) {
		return categoryRepo.updateStatusByUUID(status, uuid);
	}

	@Override
	public boolean save(CategoryFrmPP categoryFrmPP) {
		
		Category category = new Category();
		category.setUuid(UUID.randomUUID().toString());
		category.setName(categoryFrmPP.getName());
		category.setRemark(categoryFrmPP.getRemark());
		category.setStatus(categoryFrmPP.getStatus());
		return categoryRepo.save(category);
	}

}
