package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.model.ApiResponse;
import com.user.model.User;
import com.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//Getting All Users
	@GetMapping("/")
	public ResponseEntity<List<User>>  getAllUsers()
	{
		List<User>  users=this.userService.getAllUsers();
		return new ResponseEntity<>(users,HttpStatus.OK);
		
	}
	
	
	//Get Single User
	@GetMapping("/{id}")
	public ResponseEntity<User>  getSingleUser(@PathVariable("id")  int id)
	{
		User user=this.userService.getUserById(id);
		return new ResponseEntity<>(user,HttpStatus.OK);
		
	}
	
	
	//Saving User
	@PostMapping("/")
	public ResponseEntity<User>  createUser(@RequestBody User user)
	{
		User savedUser=this.userService.saveUser(user);
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
		
	}
	
	
	//Updating User
	@PutMapping("/{id}")
	public ResponseEntity<User>  updateUser(@RequestBody User user ,@PathVariable("id")  int id)
	{
		User updatedUser=this.userService.updateUser(user, id);
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
		
	}
	
	//Deleting User
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id")  int id)
	{
	      this.userService.deleteUser(id);
		return new ResponseEntity<>(new ApiResponse("User Deleted Succesfully"),HttpStatus.OK);
		
	}
	
	
	
	
	

}
