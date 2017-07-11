package com.hrd.spring.service;

import java.util.List;
import com.hrd.spring.entities.Category;
import com.hrd.spring.entities.Form.CategoryFrmPP;

public interface CategoryService {
	
	Category findByUUID(String uuid);
	
	List<Category> findAll();

	boolean update(CategoryFrmPP categoryFrmPP);

	boolean delete(String uuid);

	boolean updateStatusByUUID(String status, String uuid);

	boolean save(CategoryFrmPP categoryFrmPP);

}
