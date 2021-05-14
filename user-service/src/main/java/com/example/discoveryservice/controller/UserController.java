package com.example.discoveryservice.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discoveryservice.dto.UserDto;
import com.example.discoveryservice.service.UserService;
import com.example.discoveryservice.vo.Greeting;
import com.example.discoveryservice.vo.RequestUser;
import com.example.discoveryservice.vo.ResponseUser;

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
		return "It's Working in User Service";
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
}
