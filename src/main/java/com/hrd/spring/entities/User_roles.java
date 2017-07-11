package com.hrd.spring.entities;

public class User_roles {
	int user_id;
	int user_role;
	public User_roles(){
		
	}
	public User_roles(int user_id, int user_role) {
		super();
		this.user_id = user_id;
		this.user_role = user_role;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getUser_role() {
		return user_role;
	}
	public void setUser_role(int user_role) {
		this.user_role = user_role;
	}
	
	

}
