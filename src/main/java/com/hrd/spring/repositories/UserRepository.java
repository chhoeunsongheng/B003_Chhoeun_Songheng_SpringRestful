package com.hrd.spring.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hrd.spring.entities.Role;
import com.hrd.spring.entities.User;
import com.hrd.spring.entities.filters.UserFilter;
import com.hrd.spring.entities.Form.UserForm;
import com.hrd.spring.repositories.sql.UserSQLBuilder;

@Repository
public interface UserRepository {
	
	@Select("SELECT * FROM users ORDER BY 1 DESC LIMIT #{pagination.limit} OFFSET #{pagination.offset} ")
	@Results(value={
			@Result(property="createdDate", column="created_date"),
			@Result(property="id", column="id"),
			@Result(property="roles", column="id",
				many = @Many(select  = "findAllUserByUserId")
	)
	})
	List<User> findAll();
	
	@Select("SELECT r.id, r.name, r.remark, r.status, r.created_date, r.index, r.uuid, ur.role_id"
			+ " FROM"
			+ " roles r INNER JOIN user_roles ur ON r.id=ur.role_id"
			+ "	WHERE ur.user_id=#{user_id}")
	@Results(value={
			@Result(property="createdDate",column="created_date")
	})
	List<User> findAllUserByUserId(@Param("user_id") int id);
	
	@Select("SELECT username, email, password, dob, gender, device,id,remark,status,index, uuid " +
			" FROM users WHERE uuid = #{uuid}")
	@Results(value={
			@Result(property="createdDate", column="created_date"),
			@Result(property="roles", column="id",
				many = @Many(select  = "findOneUserByUserId")
	)
	})
	User findByUUID(String uuid);
	
	@Select("SELECT r.id,r.name,r.remark,r.status,r.created_date,r.index,r.uuid,ur.role_id"
			+ " FROM"
			+ " roles r INNER JOIN user_roles ur ON r.id=ur.role_id"
			+ "	WHERE ur.user_id=#{user_id}")
	@Results(value={
			@Result(property="createdDate",column="created_date")
	})
	User findOneUserByUserId(@Param("user_id") int id);
	
	@Update("UPDATE users SET id=#{u.id},username=#{u.username},uuid=#{u.uuid},email=#{u.email},"
			+ " password=#{u.password},gender=#{u.gender},status=#{u.status},remark=#{u.remark}"
			+ " WHERE uuid=#{u.uuid} AND Status='1'")
	@Results(value={
			@Result(property="roles", column="id",
				many = @Many(select  = "UpdateRoleByUUID")
	),
			@Result(property="user_roles", column="id",
			many = @Many(select  = "UpdateUser_roleByUUID")
	)
	})
	boolean update(@Param("u")UserForm userfrm);
	
	@Update("UPDATE users SET status=#{status} WHERE uuid=#{uuid}")
	    boolean updateStatusByUUID(@Param("status")String status, @Param("uuid")String uuid);
	
	 @Update("UPDATE users SET status = '0' WHERE uuid = #{uuid}")
	    boolean deleteUserByUUID(@Param("uuid") String uuid);


	

/*
 * find user(filter)
 */
	
//	 @SelectProvider(type = UserSQLBuilder.class, method = "myUserFilter")
	@SelectProvider(type = UserSQLBuilder.class, method = "myUserFilter")
	    @Results(value = {
	            @Result(column = "created_date", property = "createdDate"),
	            @Result(column = "id", property = "roles",
	                many = @Many(select = "findAllUserByUserId"))
	    })
	    List<User> userFilter(@Param("userFilter")UserFilter userFilter);
	

    @Insert("INSERT INTO users " +
            "(username, email, password, dob, gender, device, uuid, status) " +
            "VALUES " +
            "(#{user.username}, #{user.email}, #{user.password}, #{user.dob}, " +
            "#{user.gender}, #{user.device}, #{user.uuid}, '1')")
    @SelectKey(
            statement = "SELECT last_value FROM users_id_seq",
            keyColumn = "last_value",
            keyProperty = "user.id",
            before = false,
            resultType = int.class)
    boolean addUser(@Param("user") UserForm user);
    
    @Update("UPDATE users SET " +
            "username = #{userForm.username}, " +
            "email =  #{userForm.email}, " +
            "password = #{userForm.password}, " +
            "dob = #{userForm.dob}, " +
            "gender = #{userForm.gender}, " +
            "device = #{userForm.device} " +
            " WHERE uuid = #{userForm.uuid}")
    @SelectKey(
            statement = "SELECT id FROM users WHERE uuid = #{userForm.uuid}",
            keyProperty = "userForm.id",
            keyColumn = "id",
            before = false,
            resultType = int.class
    )
    boolean updateUser(@Param("userForm") UserForm userForm);
    
    /**
     * Count total users
     *
     * @return
     */
    @Select("SELECT COUNT(*) as total_user FROM users WHERE status = '1'")
    int countTotalUsers();

    /**
     * Count Total Male
     *
     * @return
     */
    @Select("SELECT COUNT(gender) FROM users WHERE gender = 'M' AND status = '1'")
    int countMale();

    /**
     * Count total Female
     *
     * @return
     */
    @Select("SELECT COUNT(gender) FROM users WHERE gender = 'F' AND status = '1'")
    int countFemale();

	
}

