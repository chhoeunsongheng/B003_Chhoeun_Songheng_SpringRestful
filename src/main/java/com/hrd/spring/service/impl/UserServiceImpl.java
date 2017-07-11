package com.hrd.spring.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrd.spring.entities.User;
import com.hrd.spring.entities.filters.UserFilter;
import com.hrd.spring.entities.Form.UserForm;
import com.hrd.spring.repositories.UserRepository;
import com.hrd.spring.repositories.RoleRepository;
import com.hrd.spring.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	private UserRepository userrepo;
	private RoleRepository roleRepo;
	@Autowired
	public UserServiceImpl(UserRepository userrepo,RoleRepository roleRepo) {
		super();
		this.userrepo = userrepo;
		this.roleRepo=roleRepo;
	}

	@Override
	public List<User> findAll() {
		return userrepo.findAll();
	}

	@Override
	public User findByUUID(String uuid) {
		
		return userrepo.findByUUID(uuid);
	}

//	@Override
//	public boolean update(UserFrm userfrm) {
//		
//		return userrepo.update(userfrm);
//	}

	@Override
	public boolean updateStatusByUUID(String status, String uuid) {
		
		return userrepo.updateStatusByUUID(status, uuid);
	}

	@Override
	public List<User> userFilter(UserFilter userFilter) {

		return userrepo.userFilter(userFilter);
	}

	@Override
	public boolean update(UserForm userfrm) {
		
		return userrepo.update(userfrm);
	}
	
//	@Override
//    public boolean updateUserStatus(String uuid, String status) {
//        return userrepo.updateUserStatus(uuid, status);
//    }
//
//    @Override
//    public boolean deleteUserByUUID(String uuid) {
//        return userrepo.deleteUserByUUID(uuid);
//    }

    @Override
    public User addUser(UserForm user) {
        user.setUuid(UUID.randomUUID().toString());
        if (userrepo.addUser(user)) {
            if (roleRepo.addUserRolesByUserId(user.getId(), user.getRoles())) {
                return userrepo.findByUUID(user.getUuid());
            }
        }
        return null;
    }
    
    @Override
    public boolean updateUser(UserForm userForm) {
        return userrepo.updateUser(userForm);
    }

    @Override
    public boolean deleteUserByUUID(String uuid) {
        return userrepo.deleteUserByUUID(uuid);
    }
    
    @Override
    public int countTotalUsers() {
        return userrepo.countTotalUsers();
    }

    @Override
    public int countMale() {
        return userrepo.countMale();
    }

    @Override
    public int countFemale() {
        return userrepo.countFemale();
    }

	

}
