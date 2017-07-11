package com.hrd.spring.repositories.sql;

import org.apache.ibatis.annotations.Param;
import com.hrd.spring.entities.filters.UserFilter;

public class UserSQLBuilder {
	 public static String myUserFilter(@Param("userFilter")UserFilter userFilter) {

	        StringBuffer sqlBuffer = new StringBuffer();

	        sqlBuffer.append("SELECT id, username, email, dob, gender, created_date, status, uuid " +
	                "FROM users");

	        // TODO: check what information is user privide to get FILTER
	        if(userFilter.getUsername() == null && userFilter.getEmail() == null && userFilter.getStatus() == null &&
	                (userFilter.getCreatedDate() == null || userFilter.getToCreatedDate() == null)) {
	            return "";
	        } else {
	            boolean checkMore = false;
	            sqlBuffer.append(" WHERE ");

	            if(userFilter.getUsername() != null) {
	                sqlBuffer.append("username LIKE '%' || #{userFilter.username} || '%' ");
	                checkMore = true;
	            }

	            if(userFilter.getEmail() != null) {
	                if(checkMore == true) {
	                    sqlBuffer.append(" OR ");
	                }
	                sqlBuffer.append(" email LIKE '%' || #{userFilter.username} || '%' ");
	            }

	            if(userFilter.getStatus() != null) {
	                if(checkMore == true) {
	                    sqlBuffer.append(" OR ");
	                }
	                sqlBuffer.append(" status LIKE '%' || #{userFilter.status} || '%' ");
	            }

	            if(userFilter.getCreatedDate() != null && userFilter.getToCreatedDate() != null) {
	                if(checkMore == true) {
	                    sqlBuffer.append(" OR ");
	                }
	                sqlBuffer.append(" created_date >= #{userFilter.createdDate} " +
	                        "AND created_date <= #{userFilter.toCreatedDate}");
	            }

	        }
	        return sqlBuffer.toString();
	    }

	    public static String findUserByUUID(@Param("uuid") String uuid) {
	        StringBuffer sqlBuffer = new StringBuffer();
	        sqlBuffer.append("SELECT id, username, email, dob, gender, created_date, status, uuid " +
	                "FROM users WHERE uuid = #{uuid}");
	        return sqlBuffer.toString();
	    }

	
	
	/*
	public static String myUserFliter(@Param("userFilter") UserFilter userfilter){
		StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT u.id,u.username,u.password,u.email,u.gender,"
					+ "u.uuid,u.status,u.remark,"
					+ "ur.user_id,ur.role_id,"
					+ "r.id,r.name,r.status"
					+ " FROM users u INNER JOIN user_roles ur ON u.id=ur.user_id"
					+ " INNER JOIN roles r ON r.id=ur.role_id");
			if(!userfilter.getUsername().equals(" ")||userfilter.getUsername()!=null){
				 buffer.append(" WHERE u.username LIKE '%' || #{userfilter.username} || '%' ");
			}
			
		return buffer.toString();
	}*/

}
