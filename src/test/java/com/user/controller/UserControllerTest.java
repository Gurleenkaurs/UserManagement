package com.user.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.model.User;
import com.user.service.UserService;

@WebMvcTest
public class UserControllerTest {

	@Autowired
	private UserController userController;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	private User user1;
	private User user2;
	
	
	@BeforeEach
	void init()
	{
		user1=new User();
		user1.setId(1);
		user1.setName("User1");
		user1.setEmail("user1@gmail.com");
		user1.setInfo("User1");
		
		user2=new User();
		user2.setId(2);
		user2.setName("User2");
		user2.setEmail("user2@gmail.com");
		user2.setInfo("User2");		
		
		
	}
	
	@Test
	void saveUser() throws JsonProcessingException, Exception {
		 
		when(userService.saveUser(any(User.class))).thenReturn(user1);
	
		this.mockMvc.perform(post("/api/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user1)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name",is(user1.getName())))
				.andExpect(jsonPath("$.email",is(user1.getEmail())));
		
	}
	
	
	@Test
	void getSingleUser() throws JsonProcessingException, Exception {
		 
		when(userService.getUserById(anyInt())).thenReturn(user2);
	
		this.mockMvc.perform(get("/api/users/{id}",2)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name",is(user2.getName())))
				.andExpect(jsonPath("$.email",is(user2.getEmail())));
		
	}
	
	@Test
	void getAllUsers() throws Exception
	{
		
	    List<User>  allUsers=new ArrayList<>();
	    allUsers.add(user1);
	    allUsers.add(user2);
		when(userService.getAllUsers()).thenReturn(allUsers);
		
		this.mockMvc.perform(get("/api/users/")
		             .contentType(MediaType.APPLICATION_JSON))
		             .andDo(print())
		             .andExpect(status().isOk())
		             .andExpect(jsonPath("$.size()",is(allUsers.size())));
	}
	
	@Test
	void updateUser() throws JsonProcessingException, Exception
	{
		when(userService.updateUser(any(User.class), anyInt())).thenReturn(user1);
		
		
		this.mockMvc.perform(put("/api/users/{id}",1)
		              .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(user1)))
                      .andDo(print())
                      .andExpect(status().isOk())
                      .andExpect(jsonPath("$.name",is(user1.getName())))
                      .andExpect(jsonPath("$.email",is(user1.getEmail())));
                      
	}
	
	
	@Test
	void deleteUser() throws Exception
	{
		doNothing().when(userService).deleteUser(anyInt());
		this.mockMvc.perform(delete("/api/users/{id}",1))
		            .andExpect(status().isOk());
		
	}
	
	@Test
	void getUserByName() throws Exception
	{

	    List<User>  allUsers=new ArrayList<>();
	    allUsers.add(user1);
	    allUsers.add(user2);
		when(userService.getUserByName(anyString())).thenReturn(allUsers);
		
		this.mockMvc.perform(get("/api/users/AllUsers/{name}",user1.getName())
		             .contentType(MediaType.APPLICATION_JSON))
		             .andDo(print())
		             .andExpect(status().isOk())
		             .andExpect(jsonPath("$.size()",is(allUsers.size())));
		            
	}
	
	

}
