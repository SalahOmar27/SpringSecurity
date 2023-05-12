package com.salah.springSecurity.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salah.springSecurity.entity.User;
import com.salah.springSecurity.entity.UserRecord;
import com.salah.springSecurity.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "https://localhost/")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<UserRecord>> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
	}

	@PostMapping("/add")
	public ResponseEntity<User> add(@RequestBody User user) {
		return ResponseEntity.ok(userService.add(user));
	}

	@GetMapping("/user/{email}")
	public ResponseEntity<Object> getByEmail(@PathVariable String email) {
		User user = userService.getUser(email);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	
	@DeleteMapping("/user/delete/{email}")
	public void delete(@PathVariable String email) {
		userService.delete(email);
	}

	
	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody User user) {
		return ResponseEntity.ok(userService.update(user));
	}

}
