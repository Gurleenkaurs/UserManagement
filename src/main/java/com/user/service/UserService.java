package com.user.service;

import java.util.List;

import com.user.model.User;

public interface UserService {
	
	User saveUser(User user);
	User getUserById(int id);
	List<User>  getAllUsers();
	User updateUser(User user,int id);
	void deleteUser(int id);
	

}
