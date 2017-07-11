package com.hrd.spring.service;

import java.util.List;
import com.hrd.spring.entities.User;
import com.hrd.spring.entities.Form.UserForm;
import com.hrd.spring.entities.filters.UserFilter;

public interface UserService {

	List<User> findAll();
	public User findByUUID(String uuid);
	//public boolean update(User user);
	boolean updateStatusByUUID(String status, String uuid);
	List<User> userFilter(UserFilter userFilter);
	boolean update(UserForm userfrm);
    User addUser(UserForm userForm);
    boolean deleteUserByUUID(String uuid);
    boolean updateUser(UserForm userForm);
    int countTotalUsers();

    int countMale();

    int countFemale();

}
