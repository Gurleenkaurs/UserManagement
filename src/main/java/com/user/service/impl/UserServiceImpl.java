package com.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.model.User;
import com.user.repository.UserRepository;
import com.user.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		User savedUser=this.userRepository.save(user);
		return savedUser;
	}

	@Override
	public User getUserById(int id) {
		User user=this.userRepository.findById(id);
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User>  users=this.userRepository.findAll();
		return users;
	}

	@Override
	public User updateUser(User user, int id) {
		// TODO Auto-generated method stub
		User userToUpdate=this.userRepository.findById(id);
		User updatedUser=this.userRepository.save(user);
		return updatedUser;
		
	}

	@Override
	public void deleteUser(int id) {
		
		User userToDelete=this.userRepository.findById(id);
		this.userRepository.delete(userToDelete);

	}

}
