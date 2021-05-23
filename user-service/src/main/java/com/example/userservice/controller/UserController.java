package com.example.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/")
public class UserController {
	
	private Environment env;
	private Greeting greeting;
	private UserService userService;

	public UserController(Environment env, Greeting greeting, UserService userService) {
		this.env = env;
		this.greeting = greeting;
		this.userService = userService;
	}

	@GetMapping("/heath-check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		
		log.info(env.getProperty("greeting.message"));
		
		return greeting.getMessage();
	}
	
	@PostMapping("/users")
	public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(user, UserDto.class);
		userService.createUser(userDto);
		
		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<ResponseUser>> getUsers() {
		
		Iterable<UserEntity> userList = userService.getUserByAll();
		
		List<ResponseUser> result = new ArrayList<>();
		userList.forEach(v ->{
			result.add(new ModelMapper().map(v, ResponseUser.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
		
		UserDto userDto = userService.getUserByUserId(userId);
		
		ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
}
