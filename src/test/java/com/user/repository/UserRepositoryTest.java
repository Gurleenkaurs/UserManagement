package com.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.user.model.User;

@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private User user1;
	private User user2;
	
	
	@BeforeEach
	void init()
	{
		user1=new User();
		user1.setId(8);
		user1.setName("User1");
		user1.setEmail("user1@gmail.com");
		user1.setPassword("user1");
		user1.setInfo("User1");

		user2=new User();
		user2.setId(9);
		user2.setName("User1");
		user2.setEmail("user2@gmail.com");
		user2.setPassword("user2");
		user2.setInfo("User2");
	}

  
	@Test
	void findByName()
	{
		
	   	userRepository.save(user1);
	   	userRepository.save(user2);
	   	
	    List<User> users= userRepository.findByName("User1");
	
	  assertThat(users.get(0).getName()).isEqualTo("User1");
	   assertThat(users.size()).isEqualTo(2);	
	 
	}

}
