package com.user.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import com.user.model.User;
import com.user.repository.UserRepository;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {
 
	@LocalServerPort
	private int port;
	
	
	private String baseUrl="http://localhost";
	private static RestTemplate restTemplate;
	
	private User user1;
	private User user2;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeAll
	public static void init() {
		restTemplate=new RestTemplate();
	}
	
	@BeforeEach
	public void beforeSetup()
	{
		baseUrl=baseUrl+":"+port+"/api/users/";
		
		user1=new User();
		user1.setName("User1");
		user1.setEmail("user1@gmail.com");
		user1.setInfo("User1");
		
		user2=new User();
		user2.setName("User2");
		user2.setEmail("user2@gmail.com");
		user2.setInfo("User2");		
		
		
	   user1= userRepository.save(user1);
	   user2=userRepository.save(user2);
	}
	
	
	@AfterEach
	public void afterEach()
	{
		userRepository.deleteAll();
	}
	
	
	@Test
	void shouldCreateUserTest() {

		User user3=new User();
		user3.setName("User3");
		user3.setEmail("user3@gmail.com");
		user3.setInfo("User3");		
		User newUser=restTemplate.postForObject(baseUrl, user3, User.class);
		assertNotNull(newUser);
		assertThat(newUser.getName()).isEqualTo(user3.getName());	
		
	}
	

	@Test
	void shouldFetchAllUsersTest() {

        List<User> users=restTemplate.getForObject(baseUrl,List.class);
		assertThat(users.size()).isEqualTo(2);
			
	}
	
	@Test
	void shouldFetchSingleUserTest() {

        User user=restTemplate.getForObject(baseUrl+"/"+user1.getId(),User.class);
	    assertNotNull(user);
	    assertEquals("User1",user.getName());
			
	}
	
	@Test
	void shouldFetchAllUsersByNameTest() {

        List<User> users=restTemplate.getForObject(baseUrl+"/AllUsers/"+user1.getName(),List.class);
		assertThat(users.size()).isEqualTo(1);
		assertNotNull(users);
			
	}
	
	@Test
	void shouldDeleteUserTest() {

        restTemplate.delete(baseUrl+"/"+user1.getId());
        List<User> users=restTemplate.getForObject(baseUrl,List.class);
		assertThat(users.size()).isEqualTo(1);	   
			
	}
	
	@Test
	void shouldUpdateUserTest() {
		 
		user1.setInfo("My name is User1");
        restTemplate.put(baseUrl+"/{id}",user1,user1.getId());
        User user=restTemplate.getForObject(baseUrl+"/"+user1.getId(),User.class);
		assertNotNull(user);
		assertEquals("My name is User1",user.getInfo());
			
	}
	
	
	
	
	
}
