package com.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.user.exception.UserNotFoundException;
import com.user.model.User;
import com.user.repository.UserRepository;
import com.user.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	private User user1;
	private User user2;
	private User user3;
	
	@BeforeEach
	void init()
	{
		
		user1=new User();
		user1.setName("User1");
		user1.setEmail("user1@gmail.com");
		user1.setPassword("user1");
		user1.setInfo("User1");

		user2=new User();
		user2.setName("User2");
		user2.setEmail("user2@gmail.com");
		user2.setPassword("user2");
		user2.setInfo("User2");
			
		
		user3=new User();
		user3.setName("User2");
		user3.setEmail("user2@gmail.com");
		user3.setPassword("user2");
		user3.setInfo("User2");
			
	}
	
	
	@Test
	@DisplayName("Save User")
	void save()
	{
		when(userRepository.save(any(User.class))).thenReturn(user1);
		User user=userService.saveUser(user1);
		assertNotNull(user);
		assertThat(user.getName()).isEqualTo("User1");
	}
	
	@Test
	@DisplayName("Get User By Id")
	void getUserById()
	{
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user1));
		User user=userService.getUserById(user1.getId());
		assertNotNull(user);
		assertThat(user.getName()).isEqualTo(user1.getName());
	}
	
	@Test
	@DisplayName("Get User By Id")
	void getUserByIdException()
	{
		when(userRepository.findById(4)).thenReturn(Optional.of(user1));
		assertThrows(RuntimeException.class ,
				()->{userService.getUserById(user1.getId());
				});
	}
	
	
	@Test
	@DisplayName("Get All Users")
	void getAllUsers()
	{
		List<User>  users=new ArrayList<>();
		users.add(user1);
		users.add(user2);
		when(userRepository.findAll()).thenReturn(users);
		List<User> allUsers=userService.getAllUsers();
		assertNotNull(allUsers);
		assertThat(allUsers.size()).isEqualTo(users.size());
		assertThat(allUsers.get(0).getName()).isEqualTo(user1.getName());
	}
	
	@Test
	@DisplayName("Get  Users By Name")
	void getUserByName()
	{
		List<User>  users=new ArrayList<>();
		users.add(user3);
		users.add(user2);
		when(userRepository.findByName("User2")).thenReturn(users);
		List<User> allUsers=userService.getUserByName("User2");
		assertNotNull(allUsers);
		assertThat(allUsers.size()).isEqualTo(users.size());
		assertThat(allUsers.get(0).getName()).isEqualTo("User2");
		assertThat(allUsers.get(1).getName()).isEqualTo("User2");
	}
	
	
	@Test
	@DisplayName("Update User")
	void updateUser()
	{

		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user1));
		when(userRepository.save(any(User.class))).thenReturn(user1);
		user1.setName("NewUser");
		User user=userService.updateUser(user1, user1.getId());
		assertNotNull(user);
		assertThat(user.getName()).isEqualTo("NewUser");
	}
	
	@Test
	@DisplayName("Delete User")
	void deleteUser()
	{

		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user1));
		doNothing().when(userRepository).delete(any(User.class));
	
	      userService.deleteUser(user1.getId());
	       verify(userRepository,times(1)).delete(user1);
	}
	
	
}
