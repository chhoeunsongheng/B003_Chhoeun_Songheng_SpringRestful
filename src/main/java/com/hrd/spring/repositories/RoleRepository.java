package com.hrd.spring.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.hrd.spring.entities.Role;

@Repository
public interface RoleRepository {
	    @Update("<script>" +
	                "INSERT INTO user_roles (user_id, role_id) VALUES " +
	                "<foreach collection='role_list' item='role' separator=','>" +
	                    " (#{user_id}, #{role.id})" +
	                "</foreach>" +
	            "</script>")
	    boolean addUserRolesByUserId(@Param("user_id") int user_id, @Param("role_list") List<Role> role_id);

}
