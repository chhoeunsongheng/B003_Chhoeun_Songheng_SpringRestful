package com.hrd.spring.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hrd.spring.entities.Category;
import com.hrd.spring.entities.Form.CategoryFrmPP;

@Repository
public interface CategoryRepository {

	@Select("SELECT "
			+ "	name, "
			+ "	uuid, "
			+ "	(select count(*) from articles where uuid=uuid) as total_article "
		+ "	FROM "
			+ "	categories "
		+ " WHERE "
			+ "	uuid=#{uuid};")
	Category findByUUID(String uuid);
	
	@Select("SELECT "
			+ "	id,name,created_date,remark,status,uuid,  "
			+ "	(select count(*) from articles where uuid=uuid) as total_article "
		+ " FROM "
			+ "	categories "
		+ "	ORDER BY id DESC ")
	@Results(value={
		@Result(property="createdDate", column="created_date")
	})
		//+ "	LIMIT #{pagination.limit} OFFSET #{pagination.offset};")
	List<Category> findAll();
	
	@Update("UPDATE categories set name=#{c.name}, remark=#{c.remark}, status=#{c.status}"
			+ " WHERE uuid=#{c.uuid}")
	boolean update(@Param("c")CategoryFrmPP categoryFrmPP);
	
	@Update("DELETE FROM categories WHERE uuid=#{uuid}")
	boolean delete(String uuid);

	@Update("UPDATE categories SET status=#{status} WHERE uuid=#{uuid}")
	boolean updateStatusByUUID(@Param("status")String status, @Param("uuid")String uuid);
	
	@Insert("INSERT INTO categories ( "
			+ "	name, remark, status, uuid"
			+ " )VALUES("
			+ " #{category.name}, #{category.remark}, "
			+ " #{category.status} , #{category.uuid}"
			+ " ) ")
	
	boolean save(@Param("category") Category category);
	
}
